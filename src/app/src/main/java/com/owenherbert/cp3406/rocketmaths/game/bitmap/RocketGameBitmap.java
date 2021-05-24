package com.owenherbert.cp3406.rocketmaths.game.bitmap;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.owenherbert.cp3406.rocketmaths.utility.Image;
import com.owenherbert.cp3406.rocketmaths.utility.Rand;

import java.util.Locale;

/**
 * The RocketGameBitmap class extends GameBitmap and is used for GameBitmap rocket objects.
 *
 * @author Owen Herbert
 */
public final class RocketGameBitmap extends GameBitmap {

    // utility constants

    // how much padding the cpu/player name should have
    private static final int NAME_PADDING = 30;
    // how much padding the cpu/player score should have
    private static final int SCORE_PADDING = 9;
    // how much padding the cpu/player rocket fumes should have
    private static final int FUME_PADDING = 20;
    // how much area the cpu/player rocket fumes should take up
    private static final int FUME_AREA = 40;
    // what characters to use in the fumes
    private static final char[] FUME_CHARACTERS = {'*', '/', '-', '+', '%', '#'};

    /**
     * Creates a RocketGameBitmap object.
     *
     * @param bitmap the associated bitmap.
     */
    public RocketGameBitmap(Bitmap bitmap, Image image) {

        super(bitmap, image);

        paint.setTextSize(30);
        paint.setColor(Color.WHITE);
        paint.setTextAlign(Paint.Align.CENTER);
    }

    /**
     * Draws the rocket game bitmap on the canvas.
     *
     * @param canvas the canvas the rocket is to be drawn on
     * @param completedPercentage the percentage the rocket has completed
     * @param nickname the nickname of the rocket
     */
    public void drawOn(Canvas canvas, double completedPercentage, String nickname,
                       boolean drawFumes) {

        int halfRocketSpriteWidth = bitmap.getWidth() / 2;
        int rocketHeight = bitmap.getHeight();

        // draw text
        String text = String.format(Locale.getDefault(), "(%d%%)",
                Math.round(completedPercentage * 100));

        canvas.drawBitmap(bitmap, x, y, paint);
        canvas.drawText(text, (x + halfRocketSpriteWidth), (y - SCORE_PADDING), paint);
        canvas.drawText(nickname, (x + halfRocketSpriteWidth),
                (y - SCORE_PADDING - NAME_PADDING), paint);

        if (drawFumes) drawFumes(canvas, x + halfRocketSpriteWidth,
                y + rocketHeight + FUME_PADDING);
    }

    /**
     * Draws fumes under the specified x and y coordinates.
     *
     * @param canvas the canvas the fumes are to be drawn on
     * @param x the starting x coordinate for the fumes
     * @param y the starting y coordinate for the fumes
     */
    private void drawFumes(Canvas canvas, int x, int y) {

        // create random position for each of the fume characters
        for (int i = 0; i < FUME_CHARACTERS.length; i++) {

            // create random fume position
            int randHeight = Rand.nextInt(FUME_AREA); // random fume height
            int randWidth = Rand.nextInt(FUME_AREA); // random fume width

            // calculate new fume x and y coordinate
            int fumeX = (i % 2 == 0) ? (x + randHeight) : (x - randHeight);
            int fumeY = y + randWidth;

            // draw on canvas
            canvas.drawText(String.valueOf(FUME_CHARACTERS[i]), fumeX, fumeY, paint);
        }
    }
}
