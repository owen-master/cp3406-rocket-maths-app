package com.owenherbert.cp3406.rocketmaths.sound;

import android.media.MediaPlayer;

/**
 * The MediaPlayerStream extends MediaPlayer and represents a media player stream.
 *
 * @author Owen Herbert
 */
public final class MediaPlayerStream extends MediaPlayer {

    // instance variables
    private boolean isInUse; // if the MediaPlayerStream is in use
    private GameSound gameSound; // what sound the MediaPlayerStream is using

    /**
     * Creates a MediaPlayerStream.
     */
    public MediaPlayerStream() {

        super();
    }

    /**
     * Recycles the MediaPlayerStream instance.
     */
    public void recycle() {

        stop();
        reset();
        setInUse(false);
        gameSound = null;
    }

    /**
     * Get is in use.
     * @return if the MediaPlayerStream is in use
     */
    public boolean isInUse() {

        return isInUse;
    }

    /**
     * Get is in use.
     * @param inUse if the MediaPlayerStream is in use
     */
    public void setInUse(boolean inUse) {

        isInUse = inUse;
    }

    /**
     * Get game sound.
     * @return the game sound
     */
    public GameSound getGameSound() {

        return gameSound;
    }

    /**
     * Set game sound.
     * @param gameSound the game sound
     */
    public void setGameSound(GameSound gameSound) {

        this.gameSound = gameSound;
    }
}
