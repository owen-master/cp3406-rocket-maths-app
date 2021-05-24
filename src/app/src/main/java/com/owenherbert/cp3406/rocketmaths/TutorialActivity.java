package com.owenherbert.cp3406.rocketmaths;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.owenherbert.cp3406.rocketmaths.utility.TutorialPage;

/**
 * The TutorialActivity class extends BaseActivity and is used for educating the user.
 *
 * @author Owen Herbert
 */
public class TutorialActivity extends BaseActivity {

    // instance variables
    TextView tutorialTitleTextView;
    TextView tutorialDescriptionTextView;
    ImageView tutorialImageView;

    TutorialPage currentTutorialPage; // the tutorial page that is currently being displayed

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
        setContentView(R.layout.activity_tutorial);

        // find views within activity
        tutorialTitleTextView = (TextView) findViewById(R.id.tutorialTitleTextView);
        tutorialDescriptionTextView = (TextView) findViewById(R.id.tutorialDescriptionTextView);
        tutorialImageView = (ImageView) findViewById(R.id.tutorialImageView);

        matchToTutorialPage(TutorialPage.PLANET_ALIGNMENT);
    }

    /**
     * Matches the activity to the specified TutorialPage.
     *
     * @param tutorialPage the TutorialPage
     */
    private void matchToTutorialPage(TutorialPage tutorialPage) {

        currentTutorialPage = tutorialPage;
        tutorialTitleTextView.setText(tutorialPage.getTitle());
        tutorialDescriptionTextView.setText(tutorialPage.getDescription());
        tutorialImageView.setImageResource(tutorialPage.getImage().getResourceId());
    }


    /**
     * Called when the previous button is clicked.
     *
     * @param view the View
     */
    public void previousButtonClicked(View view) {

        int previousTutorialIndex = currentTutorialPage.getId() - 1;
        if (previousTutorialIndex < 0) previousTutorialIndex = TutorialPage.values().length - 1;

        matchToTutorialPage(TutorialPage.values()[previousTutorialIndex]);
    }

    /**
     * Called when the next button is clicked.
     *
     * @param view the View
     */
    public void nextButtonClicked(View view) {

        int nextTutorialIndex = currentTutorialPage.getId() + 1;
        if (nextTutorialIndex >= TutorialPage.values().length) nextTutorialIndex = 0;

        matchToTutorialPage(TutorialPage.values()[nextTutorialIndex]);
    }
}