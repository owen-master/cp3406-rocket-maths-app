package com.owenherbert.cp3406.rocketmaths.utility;

import android.content.SharedPreferences;

import com.owenherbert.cp3406.rocketmaths.game.GameDifficulty;

/**
* The AppPreferences class is used to get access to saved settings
* faster.
*/
public class AppPreferences {

    // utility constants
    public static final String SPREF_KEY = "appPreferences";
    public static final String SPREF_GAME_DIFFICULTY = "GAME_DIFFICULTY";
    public static final String SPREF_MUSIC_ENABLED = "MUSIC_ENABLED";
    private static final boolean SOUND_ENABLED_BY_DEFAULT = true;

    private final SharedPreferences sharedPreferences;

    /**
     * Creates a AppPreferences object.
     *
     * @param sharedPreferences the shared preferences
     */
    public AppPreferences(SharedPreferences sharedPreferences) {

        this.sharedPreferences = sharedPreferences;
    }

    /**
    * Gets the set GameDifficulty.
     *
     * @return the set GameDifficulty
    */
    public GameDifficulty getGameDifficulty() {

        String gameDifficultyString = sharedPreferences.getString(SPREF_GAME_DIFFICULTY,
                GameDifficulty.EASY.name());

        for (GameDifficulty gameDifficulty : GameDifficulty.values()) {

            if (gameDifficultyString.equals(gameDifficulty.name())) {

                return gameDifficulty;
            }
        }

        return GameDifficulty.EASY;
    }

    /**
    * Set GameDifficulty.
     *
     * @param gameDifficulty the game difficulty
    */
    public void setGameDifficulty(GameDifficulty gameDifficulty) {

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(SPREF_GAME_DIFFICULTY, gameDifficulty.name());
        editor.apply();
    }

    /**
     *  Check if music is enabled.
     *
     * @return if music is enabled
     */
    public boolean getMusicEnabled() {

        return sharedPreferences.getBoolean(SPREF_MUSIC_ENABLED, SOUND_ENABLED_BY_DEFAULT);
    }

    /**
     * Set sound enabled.
     */
    public void setSoundEnabled(boolean soundEnabled) {

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(SPREF_MUSIC_ENABLED, soundEnabled);
        editor.apply();
    }
}
