package com.owenherbert.cp3406.rocketmaths.game.bitmap;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.owenherbert.cp3406.rocketmaths.utility.Image;

/**
 * The ParallaxGameBitmap class extends GameBitmap and is used for GameBitmap objects that are
 * intended for parallax use.
 *
 * @author Owen Herbert
 */
public final class ParallaxGameBitmap extends GameBitmap {

    // instance variables
    private final int parallaxMultiplier; // the parallax multiplier

    /**
     * Creates a ParallaxGameBitmap object.
     *
     * @param bitmap the associated bitmap.
     * @param parallaxMultiplier the parallax multiplier
     */
    public ParallaxGameBitmap(Bitmap bitmap, Image image, int parallaxMultiplier) {

        super(bitmap, image);
        this.parallaxMultiplier = parallaxMultiplier;
    }

    /**
     * Draws the rocket game bitmap on the canvas.
     *
     * @param canvas the canvas the ParallaxGameBitmap is to be drawn on
     */
    public void drawOn(Canvas canvas) {


        canvas.drawBitmap(bitmap, x, y, paint);
    }

    /**
     * Get the parallax multiplier.
     *
     * @return the parallax multiplier
     */
    public int getParallaxMultiplier() {

        return parallaxMultiplier;
    }
}
