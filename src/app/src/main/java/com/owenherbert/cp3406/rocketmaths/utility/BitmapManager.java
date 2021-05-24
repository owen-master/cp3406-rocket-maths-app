package com.owenherbert.cp3406.rocketmaths.utility;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.owenherbert.cp3406.rocketmaths.game.bitmap.ParallaxGameBitmap;
import com.owenherbert.cp3406.rocketmaths.game.bitmap.RocketGameBitmap;

/**
 * The BitmapManager class is an abstraction layer for loading bitmaps.
 *
 * @author Owen Herbert
 */
public class BitmapManager {

    private final Resources resources; // the resources

    /**
     * Creates a BitmapManager object.
     *
     * @param resources the resources
     */
    public BitmapManager(Resources resources) {

        this.resources = resources;
    }

    /**
     * Returns a RocketGameBitmap.
     *
     * @param image the image
     * @return a RocketGameBitmap
     */
    public RocketGameBitmap getRocketGameBitmap(Image image) {

        return new RocketGameBitmap(getBitmapByResourceId(image.getResourceId()), image);
    }

    /**
     * Creates and returns a scaled ParallaxGameBitmap.
     *
     * @param image the image
     * @param scaledSize the scale
     * @param parallaxMultiplier the speed multiplier
     * @return a ParallaxGameBitmap
     */
    public ParallaxGameBitmap getParallaxGameBitmap(Image image, int scaledSize,
                                                    int parallaxMultiplier) {

        Bitmap bitmap = getBitmapByResourceId(image.getResourceId());
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, scaledSize, scaledSize, false);

        return new ParallaxGameBitmap(scaledBitmap, image, parallaxMultiplier);
    }

    /**
     * Returns a Bitmap for the provided resource id.
     *
     * @param resourceId the resource id
     */
    private Bitmap getBitmapByResourceId(int resourceId) {

        return BitmapFactory.decodeResource(resources, resourceId);
    }
}
