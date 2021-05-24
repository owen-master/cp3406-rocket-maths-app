package com.owenherbert.cp3406.rocketmaths.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.owenherbert.cp3406.rocketmaths.utility.BitmapManager;
import com.owenherbert.cp3406.rocketmaths.utility.Image;
import com.owenherbert.cp3406.rocketmaths.R;
import com.owenherbert.cp3406.rocketmaths.utility.Rand;
import com.owenherbert.cp3406.rocketmaths.game.bitmap.GameBitmap;
import com.owenherbert.cp3406.rocketmaths.game.bitmap.ParallaxGameBitmap;
import com.owenherbert.cp3406.rocketmaths.game.bitmap.RocketGameBitmap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
* The ParallaxView class extends View and is custom to the game. The ParallaxView
* will display variables of the RocketMaths class in a form that is visually
* appealing.
 *
 * @author Owen Herbert
*/
public class ParallaxView extends View {

    // utility constants
    private static final int ROCKET_START_PADDING = 500; // padding the rocket has at the start
    private static final int ROCKET_END_PADDING = 300; // padding the rocket has on the end
    private static final int PLANET_PROXIMITY_BUFFER = 15; // proximity to be considered aligned
    private static final int NUM_PARALLAX = 14; // how many parallax bitmaps should be created

    // instance variables
    private int viewWidth; // the width of the view
    private int viewHeight; // the height of the view

    private RocketMaths rocketMaths;
    private final Bitmap rocketSprite; // the rocket bitmap
    private final RocketGameBitmap userRocketBitmap; // the user rocket bitmap
    private final RocketGameBitmap cpuRocketBitmap; // the cpu rocket bitmap
    private final List<ParallaxGameBitmap> parallaxBackgroundBitmaps; // parallax background bitmaps
    private final BitmapManager bitmapManager;

    /**
     * Creates a ParallaxView.
     *
     * @param context the context
     * @param attrs the attribute set
     */
    public ParallaxView(Context context, AttributeSet attrs) {

        super(context, attrs);

        parallaxBackgroundBitmaps = new ArrayList<>();
        bitmapManager = new BitmapManager(getResources());

        rocketSprite = BitmapFactory.decodeResource(getResources(), R.drawable.rocket);

        cpuRocketBitmap = bitmapManager.getRocketGameBitmap(Image.ROCKET);
        userRocketBitmap = bitmapManager.getRocketGameBitmap(Image.ROCKET);
    }
    
    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        // set canvas width and height
        viewWidth = canvas.getWidth();
        viewHeight = canvas.getHeight();

        // create the parallax background bitmaps if they have not been created yet
        if (parallaxBackgroundBitmaps.isEmpty()) createParallaxBitmaps();

        // only draw if the game is not paused
        if (!rocketMaths.isPaused()) {

            // calculate the progress percentage of both the cpu and the player based on scores
            int maxPoints = RocketMaths.MAX_POINTS;

            double cpuPercentage = ((rocketMaths.getCpuPoints() * 100.) / maxPoints) / 100.;
            double userPercentage = ((rocketMaths.getUserPoints() * 100.) / maxPoints) / 100.;

            int halfRocketSpriteWidth = rocketSprite.getWidth() / 2;

            drawParallaxBackgroundBitmaps(canvas);

            int actHeight = viewHeight - ROCKET_START_PADDING;

            // cpu rocket drawing
            int cpuRocketY = (int) (actHeight - ((actHeight - ROCKET_END_PADDING) * cpuPercentage));
            int cpuRocketX = (viewWidth / 4) - halfRocketSpriteWidth;

            // only draw user rocket fumes if the user has submitted an answer
            boolean drawUserFumes = rocketMaths.getRoundNumber() > 1;

            cpuRocketBitmap.setX(cpuRocketX);
            cpuRocketBitmap.setY(cpuRocketY);
            cpuRocketBitmap.drawOn(canvas, cpuPercentage, getContext().getString(R.string.cpu),
                    true);

            // user rocket drawing
            int userRocketY = (int) (actHeight - ((actHeight - ROCKET_END_PADDING) * userPercentage));
            int userRocketX = (viewWidth / 2) + (viewWidth / 4) - halfRocketSpriteWidth;

            userRocketBitmap.setX(userRocketX);
            userRocketBitmap.setY(userRocketY);
            userRocketBitmap.drawOn(canvas, userPercentage, rocketMaths.getNickname(), drawUserFumes);
        }
    }

    /**
    * Fills the parallax bitmaps variable with created space background parallax bitmaps.
    * Each of the created bitmaps has a random X, Y, image, scale and parallax multiplier.
    */
    private void createParallaxBitmaps() {

        ArrayList<Image> parallaxImages = new ArrayList<>();

        for (int i = 0; i < Image.values().length; i++) {

            if (Image.values()[i].isIntendedForParallax()) parallaxImages.add(Image.values()[i]);
        }

        for (int i = 0; i < NUM_PARALLAX; i++) {

            // create a random location for the parallax bitmap
            int randX = Rand.nextInt(viewWidth);
            int randY = - Rand.nextInt(8000);

            // assign a random bitmap
            int randBitmap = Rand.between(0, parallaxImages.size());
            int randParallaxMultiplier = Rand.nextInt(9) + 1;

            // calculate scaled size
            int scaledSize = 400 - (30 * randParallaxMultiplier);

            // create parallax bitmap
            ParallaxGameBitmap gameBitmap = bitmapManager.getParallaxGameBitmap(
                    parallaxImages.get(randBitmap), scaledSize, randParallaxMultiplier);

            gameBitmap.setX(randX);
            gameBitmap.setY(randY);

            // add parallax bitmap to array list
            parallaxBackgroundBitmaps.add(gameBitmap);
        }

        // sort parallax bitmaps by multiplier/speed - faster objects in the foreground
        Collections.sort(parallaxBackgroundBitmaps, (o1, o2) -> {
            if(o1.getParallaxMultiplier() == o2.getParallaxMultiplier())
                return 0;
            return o1.getParallaxMultiplier() < o2.getParallaxMultiplier() ? -1 : 1;
        });
    }

    /**
    * Draws each of the parallax bitmaps on the view canvas.
     *
     * @param canvas the canvas to be drawn on
    */
    private void drawParallaxBackgroundBitmaps(Canvas canvas) {

        // for each created parallax bitmap
        for (ParallaxGameBitmap parallaxGameBitmap : parallaxBackgroundBitmaps) {

            // only draw the parallax bitmap if it is within the view bounds
            if (isVisibleOnView(parallaxGameBitmap)) {

                parallaxGameBitmap.drawOn(canvas);
            }

            // update position of parallax bitmap
            int newY = parallaxGameBitmap.getY() + parallaxGameBitmap.getParallaxMultiplier();
            parallaxGameBitmap.setY(newY);
        }
    }

    /**
     * Removes planets that are aligned and returns true, if there are no aligned planets then
     * false is returned.
     *
     * @return if planets were removed
     */
    public boolean removeAlignedPlanets() {

        List<ParallaxGameBitmap> planetParallaxGameBitmaps = new ArrayList<>();

        // find parallax game bitmaps that are planets and that are visible on screen
        for (ParallaxGameBitmap parallaxGameBitmap : parallaxBackgroundBitmaps) {

            boolean isLargePlanet = parallaxGameBitmap.getImage() == Image.PLANET_ONE ||
                    parallaxGameBitmap.getImage() == Image.PLANET_TWO;

            if (isLargePlanet && isVisibleOnView(parallaxGameBitmap)) {
                planetParallaxGameBitmaps.add(parallaxGameBitmap);
            }
        }

        // check vertical proximity between large planets
        for (ParallaxGameBitmap pGB : planetParallaxGameBitmaps) {

            int yStart = pGB.getY() - PLANET_PROXIMITY_BUFFER;
            int yEnd = pGB.getY() + PLANET_PROXIMITY_BUFFER;

            for (ParallaxGameBitmap nestedPGB : planetParallaxGameBitmaps) {

                if (pGB != nestedPGB && (nestedPGB.getY() > yStart && nestedPGB.getY() < yEnd)) {

                    // remove planets from view
                    parallaxBackgroundBitmaps.remove(pGB);
                    parallaxBackgroundBitmaps.remove(nestedPGB);

                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Returns if the specified GameBitmap is visible on the view.
     *
     * @return if the GameBitmap is visible on the view
     */
    private boolean isVisibleOnView(GameBitmap gameBitmap) {

        return gameBitmap.getY() > -gameBitmap.getBitmap().getHeight()
                && gameBitmap.getY() < viewHeight;
    }

    /**
     * Set rocket maths
     */
    public void setRocketMaths(RocketMaths rocketMaths) {

        this.rocketMaths = rocketMaths;
    }
}


