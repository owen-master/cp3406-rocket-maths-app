package com.owenherbert.cp3406.rocketmaths;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.owenherbert.cp3406.rocketmaths.database.AppDatabase;
import com.owenherbert.cp3406.rocketmaths.sound.SoundManager;
import com.owenherbert.cp3406.rocketmaths.utility.AppPreferences;

/**
* The BaseActivity class holds instance variables and methods that are commonly used in other
 * inherited activities.
 *
 * @author Owen Herbert
*/
public class BaseActivity extends AppCompatActivity {

    // instance variables
    AppPreferences appPreferences;
    SoundManager soundManager;
    AppDatabase appDatabase;

    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState  If the activity is being re-initialized after previously being
     *                            shut down then this Bundle contains the data it most recently
     *                            supplied in onSaveInstanceState(Bundle). Note: Otherwise it is
     *                            null. This value may be null.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // set app database
        appDatabase = AppDatabase.getInstance(this);

        // set app sound manager
        soundManager = new SoundManager(this);

        // set app preferences
        SharedPreferences sharedPreferences = getSharedPreferences(AppPreferences.SPREF_KEY,
                Context.MODE_PRIVATE);
        appPreferences = new AppPreferences(sharedPreferences);
    }

    /**
     * Starts the specified intent with a fade in/out animation also destroys the current activity.
     *
     * @param intent the intent
     */
    @Override
    public void startActivity(Intent intent) {

        finish();
        super.startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    /**
     * Called when the home button is clicked.
     *
     * @param view the View
     */
    public void homeButtonClicked(View view) {

        Intent intent = new Intent(this, LandingActivity.class);
        startActivity(intent);
    }
}
