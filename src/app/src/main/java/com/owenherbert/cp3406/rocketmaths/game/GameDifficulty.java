package com.owenherbert.cp3406.rocketmaths.game;

import com.owenherbert.cp3406.rocketmaths.sound.GameSound;

/**
* The GameDifficulty enum represents a game difficulty within the game.
 *
 * @author Owen Herbert
*/
public enum GameDifficulty {

    EASY(
            1,
            4,
            2,
            10,
            40,
            20,
            1,
            new OperationType[]{
                    OperationType.ADDITION
            },
            GameSound.EASY_MUSIC
    ),

    MEDIUM(
            1,
            8,
            2,
            10,
            40,
            30,
            1,
            new OperationType[]{
                    OperationType.ADDITION,
                    OperationType.SUBTRACTION
            },
            GameSound.MEDIUM_MUSIC
    ),

    HARD(
            3,
            12,
            3,
            15,
            70,
            40,
            1,
            new OperationType[]{
                    OperationType.ADDITION,
                    OperationType.SUBTRACTION,
                    OperationType.MULTIPLICATION
            },
            GameSound.HARD_MUSIC
    ),

    EXTREME(
            1,
            12,
            3,
            20,
            120,
            40,
            1,
            new OperationType[]{
                    OperationType.ADDITION,
                    OperationType.SUBTRACTION,
                    OperationType.MULTIPLICATION,
                    OperationType.DIVISION
            },
            GameSound.EXTREME_MUSIC
    );

    // instance variables
    private final int minimumNumber;
    private final int maximumNumber;
    private final int lengthOfEquation;
    private final int workingNumberReminderDeduction;
    private final int pointsCorrect;
    private final int pointsIncorrect;
    private final int cpuPointsMultiplier;
    private final OperationType[] operationTypes;
    private final GameSound backgroundMusic;

    /**
     * Creates a GameDifficulty.
     *
     * @param minimumNumber the minimum number that can be part of an equation
     * @param maximumNumber the maximum number that can be part of an equation
     * @param lengthOfEquation how many number slots should be in the equation
     * @param workingNumberReminderDeduction the amount of points to deduct if the user needs to be
     *                                       reminded of the working number
     * @param pointsCorrect amount of points user gets when an answer is correct
     * @param pointsIncorrect amount of points user loses when an answer is incorrect
     * @param cpuPointsMultiplier the multiplier of cpu points
     * @param operationTypes operations that can be included in the equation
     * @param gameSound the sound for the difficulty
     */
    GameDifficulty(int minimumNumber, int maximumNumber, int lengthOfEquation, int workingNumberReminderDeduction, int pointsCorrect, int pointsIncorrect,
                   int cpuPointsMultiplier, OperationType[] operationTypes, GameSound gameSound) {

        this.minimumNumber = minimumNumber;
        this.maximumNumber = maximumNumber;
        this.lengthOfEquation = lengthOfEquation;
        this.workingNumberReminderDeduction = workingNumberReminderDeduction;
        this.pointsCorrect = pointsCorrect;
        this.pointsIncorrect = pointsIncorrect;
        this.cpuPointsMultiplier = cpuPointsMultiplier;
        this.operationTypes = operationTypes;
        this.backgroundMusic = gameSound;
    }

    /**
     * Get the minimum number.
     * @return the minimum number
     */
    public int getMinimumNumber() {
        
        return minimumNumber;
    }

    /**
     * Get the maximum number.
     * @return the maximum number
     */
    public int getMaximumNumber() {

        return maximumNumber;
    }

    /**
     * Get the length of equation.
     * @return the length of equation
     */
    public int getLengthOfEquation() {

        return lengthOfEquation;
    }

    /**
     * Get the working number reminder deduction.
     * @return the working number reminder deduction
     */
    public int getWorkingNumberReminderDeduction() {
        return workingNumberReminderDeduction;
    }

    /**
     * Get the points correct.
     * @return the points correct
     */
    public int getPointsCorrect() {

        return pointsCorrect;
    }

    /**
     * Get the points incorrect.
     * @return the points incorrect
     */
    public int getPointsIncorrect() {

        return pointsIncorrect;
    }

    /**
     * Get the cpu points multiplier
     * @return the cpu points multiplier
     */
    public int getCpuPointsMultiplier() {

        return cpuPointsMultiplier;
    }

    /**
     * Get the operation types.
     * @return the operation types
     */
    public OperationType[] getOperationTypes() {

        return operationTypes;
    }

    /**
     * Get the background music.
     * @return the background music
     */
    public GameSound getBackgroundMusic() {

        return backgroundMusic;
    }
}
