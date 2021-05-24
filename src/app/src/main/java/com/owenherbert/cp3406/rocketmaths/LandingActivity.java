package com.owenherbert.cp3406.rocketmaths;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * The LandingActivity class extends BaseActivity and is used as a navigation hub between settings,
 * highscores and game play.
 *
 * @author Owen Herbert
 */
public class LandingActivity extends BaseActivity {

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
        setContentView(R.layout.activity_landing);
    }

    /**
     * Called when the new game button is clicked.
     *
     * @param view the View
     */
    public void newGameButtonClicked(View view) {

        Intent intent = new Intent(this, PlayersActivity.class);
        startActivity(intent);
    }

    /**
     * Called when the highscores button is clicked.
     *
     * @param view the View
     */
    public void highscoresButtonClicked(View view) {

        Intent intent = new Intent(this, HighscoresActivity.class);
        startActivity(intent);
    }

    /**
     * Called when the tutorial button is clicked.
     *
     * @param view the View
     */
    public void tutorialButtonClicked(View view) {

        Intent intent = new Intent(this, TutorialActivity.class);
        startActivity(intent);
    }

    /**
     * Called when the settings button is clicked.
     *
     * @param view the View
     */
    public void settingsButtonClicked(View view) {

        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}