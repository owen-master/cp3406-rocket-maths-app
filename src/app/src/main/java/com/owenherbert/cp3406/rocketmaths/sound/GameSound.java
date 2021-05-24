package com.owenherbert.cp3406.rocketmaths.sound;

import com.owenherbert.cp3406.rocketmaths.R;

/**
 * The GameSound enum represents a raw sound resource file.
 *
 * @author Owen Herbert
 */
public enum GameSound {

    CORRECT(R.raw.correct, false),
    CONFIRM(R.raw.confirm, false),
    CANCEL(R.raw.cancel, false),
    POP(R.raw.pop, false),
    CLOCK(R.raw.clock, false),
    CONGRATULATIONS(R.raw.congratulations, false),
    EASY_MUSIC(R.raw.music_gamemode_easy, true),
    MEDIUM_MUSIC(R.raw.music_gamemode_medium, true),
    HARD_MUSIC(R.raw.music_gamemode_hard, true),
    EXTREME_MUSIC(R.raw.music_gamemode_extreme, true);

    // instance variables
    private final int resourceId;
    private final boolean isMusic;

    /**
     * Creates a GameSound.
     *
     * @param resourceId the android resource id of the sound
     * @param isMusic if the sound is a music track
     */
    GameSound(int resourceId, boolean isMusic) {

        this.resourceId = resourceId;
        this.isMusic = isMusic;
    }

    /**
     * Get the resource id.
     *
     * @return the resource id
     */
    public int getResourceId() {

        return resourceId;
    }

    /**
     * Get is music.
     *
     * @return if the game sound is music
     */
    public boolean isMusic() {

        return isMusic;
    }
}
