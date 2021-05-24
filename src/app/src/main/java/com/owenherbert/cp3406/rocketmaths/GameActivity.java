package com.owenherbert.cp3406.rocketmaths;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.owenherbert.cp3406.rocketmaths.database.entity.result.Result;
import com.owenherbert.cp3406.rocketmaths.game.ParallaxView;
import com.owenherbert.cp3406.rocketmaths.game.RocketMaths;
import com.owenherbert.cp3406.rocketmaths.sound.GameSound;
import com.owenherbert.cp3406.rocketmaths.utility.ShakeListener;
import com.owenherbert.cp3406.rocketmaths.utility.ThreadRunner;

import java.util.ArrayList;
import java.util.Locale;

/**
 * The GameActivity class extends BaseActivity and is used for game play.
 *
 * @author Owen Herbert
 */
public class GameActivity extends BaseActivity {

    // utility constants
    private static final double END_PROXIMITY_MULTIPLIER = 0.9;
    private static final int DELAY_MS_UPDATE = 70; // how often in ms the view should be updated

    // instance variables
    private MenuItem pauseButton;
    private MenuItem homeButton;
    private LinearLayout gameInfoLinearLayout;
    private ParallaxView parallaxView;
    private TableRow possibleAnswersTableRow;
    private Button answerButton1;
    private Button answerButton2;
    private Button answerButton3;
    private Button answerButton4;
    private Button answerButton5;
    private Button playButton;
    private TextView readyTextView;
    private TextView pausedTextView;
    private TextView roundNumberTextView;
    private TextView roundEquationTextView;

    private ShakeListener shakeListener;
    private SensorManager sensorManager;

    private RocketMaths rocketMaths;

    private Handler parallaxViewHandler;

    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState  If the activity is being re-initialized after previously being
     *                            shut down then this Bundle contains the data it most recently
     *                            supplied in onSaveInstanceState(Bundle). Note: Otherwise it is
     *                            null. This value may be null.
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // find views within activity
        playButton = findViewById(R.id.playButton);
        answerButton1 = findViewById(R.id.answerButton1);
        answerButton2 = findViewById(R.id.answerButton2);
        answerButton3 = findViewById(R.id.answerButton3);
        answerButton4 = findViewById(R.id.answerButton4);
        answerButton5 = findViewById(R.id.answerButton5);
        readyTextView = findViewById(R.id.readyTextView);
        pausedTextView = findViewById(R.id.pausedTextView);
        parallaxView = findViewById(R.id.gameBarsGameView);
        roundNumberTextView = findViewById(R.id.roundNumberTextView);
        gameInfoLinearLayout = findViewById(R.id.gameInfoLinearLayout);
        roundEquationTextView = findViewById(R.id.roundEquationTextView);
        possibleAnswersTableRow = findViewById(R.id.possibleAnswersTableRow);

        // create RocketMaths game object
        rocketMaths = new RocketMaths(appPreferences.getGameDifficulty());
        rocketMaths.setNickname(getIntent().getStringExtra("NICKNAME"));

        parallaxView.setRocketMaths(rocketMaths);

        matchInterfaceToRound();

        // display the working number to user on click
        roundEquationTextView.setOnClickListener(view -> showWorkingNumber());

        // display the working number on double tap
        roundEquationTextView.setOnTouchListener(new View.OnTouchListener() {

            GestureDetector gestureDetector = new GestureDetector(getApplicationContext(), new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    showWorkingNumber();
                    return super.onDoubleTap(e);
                }
            });

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                gestureDetector.onTouchEvent(motionEvent);
                return false;
            }
        });


        // setup shake listener
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        shakeListener = new ShakeListener();

        shakeListener.setOnShakeListener(() -> {

            if (parallaxView.removeAlignedPlanets()) {

                soundManager.play(GameSound.CONGRATULATIONS);
                rocketMaths.addUserPoints(RocketMaths.NUM_POINTS_PLANET_ALIGNMENT);
            }
        });

        sensorManager.registerListener(shakeListener, sensorAccelerometer,
                SensorManager.SENSOR_DELAY_UI);

        // create and start parallax view handler
        parallaxViewHandler = new Handler(Looper.getMainLooper());
        parallaxViewHandler.post(createGameRunnable());
    }

    /**
     * Called as part of the activity lifecycle when the user no longer actively interacts with the
     * activity, but it is still visible on screen. The counterpart to onResume().
     */
    @Override
    protected void onPause() {

        super.onPause();
        sensorManager.unregisterListener(shakeListener);
    }

    /**
     * Called when you are no longer visible to the user. You will next receive either onRestart(),
     * onDestroy(), or nothing, depending on later user activity. This is a good place to stop
     * refreshing UI, running animations and other visual things.
     */
    @Override
    protected void onStop() {

        super.onStop();
        soundManager.stopAll();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        parallaxViewHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.game_menu, menu);
        pauseButton = menu.findItem(R.id.pauseButton);
        homeButton = menu.findItem(R.id.homeButton);

        // menu pause button
        pauseButton.setOnMenuItemClickListener(item -> {

            if (rocketMaths.isPaused()) { // play

                soundManager.play(GameSound.CONFIRM);
                soundManager.playPausedMusic();
                rocketMaths.setPaused(false);
                showGameComponents();
            } else { // pause

                soundManager.play(GameSound.CANCEL);
                soundManager.pausePlayingMusic();
                rocketMaths.setPaused(true);
                hideGameComponents();
            }

            matchPauseButtonToState();
            return false;
        });

        // menu home button
        homeButton.setOnMenuItemClickListener(item -> {

            Intent intent = new Intent(GameActivity.this, LandingActivity.class);
            startActivity(intent);

            return false;
        });

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Sets the visibility of game components to the paused state. In this state math components
     * will be invisible so the user cannot cheat. A paused text view will also be made visible.
     */
    private void hideGameComponents() {

        pausedTextView.setVisibility(View.VISIBLE);
        gameInfoLinearLayout.setVisibility(View.INVISIBLE);
        possibleAnswersTableRow.setVisibility(View.INVISIBLE);
    }

    /**
     * Sets the visibility of game components to the playing state. In this state math components
     * will be visible.
     */
    private void showGameComponents() {

        pausedTextView.setVisibility(View.INVISIBLE);
        gameInfoLinearLayout.setVisibility(View.VISIBLE);
        possibleAnswersTableRow.setVisibility(View.VISIBLE);
    }

    /**
     * Displays the working number if it is not already visible. If the working number is shown,
     * a sound will be played and points will be deducted.
     */
    private void showWorkingNumber() {

        if (!rocketMaths.isWorkingNumberExposed()) {

            rocketMaths.setWorkingNumberExposed(true);
            soundManager.play(GameSound.POP);
            roundEquationTextView.setText(rocketMaths.getRoundEquation().getEquationString());
            rocketMaths.removeUserPoints(rocketMaths.getGameDifficulty()
                    .getWorkingNumberReminderDeduction());
        }
    }

    /**
     * Matches the interface to the the game.
     */
    private void matchInterfaceToRound() {

        roundNumberTextView.setText(String.format(getString(R.string.game_round_format),
                rocketMaths.getRoundNumber()));

        if (rocketMaths.isFirstRound()) {
            roundEquationTextView.setText(rocketMaths.getRoundEquation().getEquationString());
        } else if (rocketMaths.isPreviousRoundWrong()) {
            roundEquationTextView.setText(rocketMaths.getRoundEquation().getEquationString());
        } else {
            roundEquationTextView.setText(rocketMaths.getRoundEquation()
                    .getEquationStringWithoutWorkingNumber());
        }

        ArrayList<Integer> possibleAnswers = rocketMaths.getRoundEquation()
                .getPossibleEquationAnswers();

        // set possible answers for round
        answerButton1.setText(String.valueOf(possibleAnswers.get(0)));
        answerButton2.setText(String.valueOf(possibleAnswers.get(1)));
        answerButton3.setText(String.valueOf(possibleAnswers.get(2)));
        answerButton4.setText(String.valueOf(possibleAnswers.get(3)));
        answerButton5.setText(String.valueOf(possibleAnswers.get(4)));
    }

    /**
     * Called when an answer button is clicked.
     *
     * @param view the View
     */
    public void answerButtonClicked(View view) {

        Button button = (Button) view;
        int buttonInt = Integer.parseInt(button.getText().toString());

        // check if answer is correct
        if (rocketMaths.isAnswerCorrect(buttonInt)) {

            // correct answer
            soundManager.play(GameSound.CORRECT);
            rocketMaths.setWorkingNumberExposed(false);
            rocketMaths.setPreviousRoundWrong(false);
            rocketMaths.increaseTallyCorrect(1);
            rocketMaths.setRoundWorkingNumber(buttonInt);
            rocketMaths.addUserPoints(rocketMaths.getGameDifficulty().getPointsCorrect());
        } else {

            // incorrect answer
            rocketMaths.setWorkingNumberExposed(true);
            rocketMaths.setPreviousRoundWrong(true);
            rocketMaths.increaseTallyIncorrect(1);
            soundManager.play(GameSound.CANCEL);
            rocketMaths.removeUserPoints(rocketMaths.getGameDifficulty().getPointsIncorrect());
        }

        rocketMaths.proceedToNextRound();
        matchInterfaceToRound();
    }

    /**
     * Matches the pause button to the state of the game.
     */
    private void matchPauseButtonToState() {

        int resourceID = rocketMaths.isPaused() ? R.drawable.ic_play : R.drawable.ic_pause;
        pauseButton.setIcon(resourceID);
    }

    /**
     * Called when the play button is clicked.
     *
     * @param view the View
     */
    public void playButtonClicked(View view) {

        playButton.setVisibility(View.INVISIBLE);
        readyTextView.setVisibility(View.INVISIBLE);
        gameInfoLinearLayout.setVisibility(View.VISIBLE);
        possibleAnswersTableRow.setVisibility(View.VISIBLE);
        rocketMaths.setPaused(false);
        pauseButton.setVisible(true);
        homeButton.setVisible(true);

        // play level music if music is enabled
        if (appPreferences.getMusicEnabled()) {

            soundManager.play(rocketMaths.getGameDifficulty().getBackgroundMusic());
        }
    }

    /**
     * Returns the game runnable.
     *
     * @return the game runnable
     */
    private Runnable createGameRunnable() {

        return new Runnable() {
            @Override
            public void run() {

                // check if the game is paused
                if (!rocketMaths.isPaused()) {

                    // add cpu points
                    int cpuPointsToAdd = rocketMaths.getGameDifficulty().getCpuPointsMultiplier();
                    rocketMaths.addCpuPoints(cpuPointsToAdd);

                    // update the round text view
                    String roundText = String.format(Locale.getDefault(),
                            getString(R.string.game_round_format), rocketMaths.getRoundNumber());

                    roundNumberTextView.setText(roundText);

                    // play clock sound if CPU is about to reach the finish line
                    int finishApproximation = (int) (RocketMaths.MAX_POINTS *
                            END_PROXIMITY_MULTIPLIER);

                    if (rocketMaths.getCpuPoints() > finishApproximation) {
                        soundManager.playIfNotPlaying(GameSound.CLOCK);
                    }

                    // check if CPU has crossed the finish line
                    if (rocketMaths.isGameOver()) {

                        soundManager.stopAll();
                        soundManager.play(GameSound.CONGRATULATIONS);

                        // perform action on new thread - add result to database
                        ThreadRunner.run(() -> {

                            String userNickname = rocketMaths.getNickname();
                            String gameDifficulty = rocketMaths.getGameDifficulty().name();

                            Result prevResult = appDatabase.resultDao()
                                    .getResults(userNickname, gameDifficulty);

                            // return if highscore does not need to be updated
                            if (prevResult != null &&
                                    rocketMaths.getUserPoints() < prevResult.getPoints()) {
                                return;
                            }

                            // update or insert highscore
                            Result result = new Result(
                                    rocketMaths.getNickname(),
                                    rocketMaths.getGameDifficulty().name(),
                                    rocketMaths.getUserPoints(),
                                    rocketMaths.getTallyCorrect(),
                                    rocketMaths.getTallyIncorrect()
                            );

                            appDatabase.resultDao().insert(result);
                        });

                        Intent intent = new Intent(GameActivity.this, HighscoresActivity.class);
                        intent.putExtra("GAME_DIFFICULTY", rocketMaths.getGameDifficulty().name());
                        startActivity(intent);

                        return;
                    }

                    parallaxView.invalidate();
                }

                // post if the game is not over
                if (!rocketMaths.isGameOver())
                    parallaxViewHandler.postDelayed(this, DELAY_MS_UPDATE);
            }
        };
    }
}