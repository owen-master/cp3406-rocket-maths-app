package com.owenherbert.cp3406.rocketmaths;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.owenherbert.cp3406.rocketmaths.database.entity.user.User;
import com.owenherbert.cp3406.rocketmaths.utility.ThreadRunner;

/**
 * The PlayersActivity class extends BaseActivity and is used for selecting a player to play
 * the game.
 *
 * @author Owen Herbert
 */
public class PlayersActivity extends BaseActivity {

    // utility constants
    private static final int MAX_PLAYERS = 4; // the max amount of players allowed to be created

    // instance variables
    private int playerCount; // how many players are in the database

    private LinearLayout playersHolderLinearLayout;

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
        setContentView(R.layout.activity_players);

        // find views within activity
        playersHolderLinearLayout = findViewById(R.id.playersHolderLinearLayout);

        // perform action on new thread - display button for each user
        ThreadRunner.run(() -> {
            // create a button for each user
            for (User user : appDatabase.userDao().getAllOrderByNickname()) {

                final int playerNumber = playerCount;
                runOnUiThread(() -> {

                    getLayoutInflater().inflate(R.layout.player_button, playersHolderLinearLayout);

                    Button button = (Button) playersHolderLinearLayout.getChildAt(playerNumber);
                    button.setText(user.getNickname());
                });

                playerCount++;
            }
        });
    }

    /**
     * Called when the new player button is clicked.
     *
     * @param view the View
     */
    public void newPlayerButtonClicked(View view) {

        // check if user is able to create a new player
        if (playerCount >= MAX_PLAYERS) {

            Toast.makeText(this, "Player limit reached!", Toast.LENGTH_SHORT).show();
        } else {

            Intent intent = new Intent(this, NewPlayerActivity.class);
            startActivity(intent);
        }
    }

    /**
     * Called when a player button is clicked.
     *
     * @param view the View
     */
    public void playerButtonClicked(View view) {

        Button button = (Button) view;

        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("NICKNAME", button.getText());
        startActivity(intent);
    }
}