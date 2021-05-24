package com.owenherbert.cp3406.rocketmaths;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.owenherbert.cp3406.rocketmaths.database.entity.user.User;
import com.owenherbert.cp3406.rocketmaths.sound.GameSound;
import com.owenherbert.cp3406.rocketmaths.utility.ThreadRunner;

/**
 * The NewPlayerActivity class extends BaseActivity and is used for creating a new player.
 *
 * @author Owen Herbert
 */
public class NewPlayerActivity extends BaseActivity {

    // instance variables
    private EditText userNameEditText;
    private Button userNameSubmitButton;

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
        setContentView(R.layout.activity_new_player);

        // find views within activity
        userNameEditText = findViewById(R.id.userNameEditText);
        userNameSubmitButton = findViewById(R.id.userNameSubmitButton);
    }

    /**
     * Called when the create player button is clicked.
     *
     * @param view the View
     */
    public void createButtonClicked(View view) {

        String trimmedUserNickname = userNameEditText.getText().toString().trim();

        // check if nickname is valid
        if (trimmedUserNickname.length() > 0 && !trimmedUserNickname.equals(" ")) {

            userNameSubmitButton.setText(R.string.status_creating);

            // perform action on new thread - create new user in database
            ThreadRunner.run(() -> {

                appDatabase.userDao().insertAll(new User(trimmedUserNickname));

                Intent intent = new Intent(this, PlayersActivity.class);
                startActivity(intent);
            });
        } else {

            soundManager.play(GameSound.CANCEL);
            Toast.makeText(this, getString(R.string.hint_please_enter_a_username),
                    Toast.LENGTH_SHORT).show();
        }
    }
}