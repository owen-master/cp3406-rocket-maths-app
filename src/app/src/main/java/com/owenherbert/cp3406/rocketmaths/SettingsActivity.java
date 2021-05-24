package com.owenherbert.cp3406.rocketmaths;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;

import com.owenherbert.cp3406.rocketmaths.game.GameDifficulty;
import com.owenherbert.cp3406.rocketmaths.utility.ThreadRunner;

/**
 * The SettingsActivity class extends BaseActivity and is used for interacting with the game
 * settings.
 *
 * @author Owen Herbert
 */
public class SettingsActivity extends BaseActivity {

    // instance variables
    private Button gameDifficultyButton;
    private Button soundButton;

    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState  If the activity is being re-initialized after previously being
     *                            shut down then this Bundle contains the data it most recently
     *                            supplied in onSaveInstanceState(Bundle). Note: Otherwise it is
     *                            null. This value may be null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // find views within activity
        gameDifficultyButton = findViewById(R.id.gameDifficultyButton);
        soundButton = findViewById(R.id.soundButton);

        // match buttons to state
        matchGameDifficultyButtonToState();
        matchMusicEnabledButtonToState();
    }

    /**
     * Matches the game difficulty button to the app state.
     */
    private void matchGameDifficultyButtonToState() {

        String btnText = getString(R.string.button_difficulty_format);
        gameDifficultyButton.setText(String.format("%s%s", btnText,
                appPreferences.getGameDifficulty().name()));
    }

    /**
     * Matches the music toggle button to the app state.
     */
    private void matchMusicEnabledButtonToState() {

        String enabledText = getString(R.string.button_music_enabled);
        String disabledText = getString(R.string.button_music_disabled);
        String btnText = (appPreferences.getMusicEnabled() ? enabledText : disabledText);

        soundButton.setText(btnText);
    }

    /**
     * Called when the game difficulty button is clicked.
     *
     * @param view the View
     */
    public void gameDifficultyButtonClicked(View view) {

        GameDifficulty[] gameDifficulties = GameDifficulty.values();
        String currentDifficultyName = appPreferences.getGameDifficulty().name();
        int amountOfDifficulties = GameDifficulty.values().length;

        for (int i = 0; i < gameDifficulties.length; i++) {

            if (currentDifficultyName.equals(gameDifficulties[i].name())) {

                // check if button should loop to beginning of difficulties
                GameDifficulty newGameDifficulty = (i < (amountOfDifficulties - 1))
                        ? gameDifficulties[i + 1] : gameDifficulties[0];
                appPreferences.setGameDifficulty(newGameDifficulty);
                matchGameDifficultyButtonToState();
            }
        }
    }

    /**
     * Called when the sound toggle button is clicked.
     *
     * @param view the View
     */
    public void musicButtonClicked(View view) {

        appPreferences.setSoundEnabled(!appPreferences.getMusicEnabled());
        matchMusicEnabledButtonToState();
    }

    /**
     * Called when the clear results button is clicked.
     *
     * @param view the View
     */
    public void clearResultsButtonClicked(View view) {

        // create and show an alert dialog
        new AlertDialog.Builder(this)
            .setMessage(getString(R.string.dialog_clear_results_description))
            .setTitle(getString(R.string.dialog_clear_results_title))
            .setPositiveButton(getString(R.string.dialog_positive),
                    (dialogInterface, i) -> clearResults())
            .setNegativeButton(getString(R.string.dialog_negative),
                    (dialogInterface, i) -> dialogInterface.dismiss())
            .create().show();
    }

    /**
     * Called when the clear players button is clicked.
     *
     * @param view the View
     */
    public void clearPlayersButtonClicked(View view) {

        // create and show an alert dialog
        new AlertDialog.Builder(this)
            .setMessage(getString(R.string.dialog_clear_players_description))
            .setTitle(getString(R.string.dialog_clear_players_title))
            .setPositiveButton(getString(R.string.dialog_positive),
                    (dialogInterface, i) -> clearPlayers())
            .setNegativeButton(getString(R.string.dialog_negative),
                    (dialogInterface, i) -> dialogInterface.dismiss())
            .create().show();
    }

    /**
     * Clears all results in the database.
     */
    private void clearResults() {

        // perform action on new thread - delete all results
        ThreadRunner.run(() -> appDatabase.resultDao().deleteAll());
    }

    /**
     * Clears all players in the database.
     */
    private void clearPlayers() {

        // perform action on new thread - delete all users
        ThreadRunner.run(() -> appDatabase.userDao().deleteAll());
    }
}