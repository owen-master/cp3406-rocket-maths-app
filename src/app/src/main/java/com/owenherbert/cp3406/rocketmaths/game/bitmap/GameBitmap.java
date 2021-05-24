package com.owenherbert.cp3406.rocketmaths.game.bitmap;

import android.graphics.Bitmap;
import android.graphics.Paint;

import com.owenherbert.cp3406.rocketmaths.utility.Image;

/**
* The GameBitmap class represents a Bitmap intended for
* game use.
 *
 * @author Owen Herbert
*/
public class GameBitmap {

    // utility constants
    private static final int DEFAULT_COORDINATE = 0; // the default x and y coordinate

    // instance variables
    protected final Bitmap bitmap; // image/sprite bitmap
    protected final Image image; // the image
    protected Paint paint;
    protected int x; // the x coordinate of the bitmap
    protected int y; // the y coordinate of the bitmap

    /**
     * Creates a GameBitmap object.
     *
     * @param bitmap the associated bitmap.
     */
    public GameBitmap(Bitmap bitmap, Image image) {

        this.bitmap = bitmap;
        this.image = image;

        paint = new Paint();

        // set coordinates to default
        this.x = DEFAULT_COORDINATE;
        this.y = DEFAULT_COORDINATE;
    }

    /**
     * Get the associated bitmap.
     *
     * @return the bitmap
     */
    public Bitmap getBitmap() {

        return bitmap;
    }

    /**
     * Get the value of X.
     *
     * @return the x value
     */
    public int getX() {

        return x;
    }

    /**
     * Set the value of X.
     *
     * @param x the x value
     */
    public void setX(int x) {

        this.x = x;
    }

    /**
     * Get the value of Y.
     *
     * @return the y value
     */
    public int getY() {

        return y;
    }

    /**
     * Set the value of Y.
     *
     * @param y the y value
     */
    public void setY(int y) {

        this.y = y;
    }

    /**
     * Get Image.
     *
     * @return the Image
     */
    public Image getImage() {

        return image;
    }
}
