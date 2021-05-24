package com.owenherbert.cp3406.rocketmaths;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.owenherbert.cp3406.rocketmaths.adapter.ResultListAdapter;
import com.owenherbert.cp3406.rocketmaths.database.entity.result.Result;
import com.owenherbert.cp3406.rocketmaths.utility.ThreadRunner;

import java.util.ArrayList;

/**
 * The HighscoresActivity class extends BaseActivity and is used for viewing game difficulty
 * highscores.
 *
 * @author Owen Herbert
 */
public class HighscoresActivity extends BaseActivity {

    // instance variables
    private TextView titleTextView;
    private ListView highscoresHolderListView;

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
        setContentView(R.layout.activity_highscores);

        // find views within activity
        highscoresHolderListView = findViewById(R.id.highscoresHolderListView);
        titleTextView = findViewById(R.id.titleTextView);

        titleTextView.setText(String.format(getString(R.string.title_highscores_format),
                appPreferences.getGameDifficulty().name()));

        // perform action on new thread - display highscores
        ThreadRunner.run(() -> {
            ArrayList<Result> results = (ArrayList<Result>) appDatabase.resultDao()
                    .getResultsByGameDifficulty(appPreferences.getGameDifficulty().name());

            ResultListAdapter resultListAdapter = new ResultListAdapter(this,
                    R.layout.player_highscore, results, appPreferences.getGameDifficulty());
            highscoresHolderListView.setAdapter(resultListAdapter);
        });
    }
}