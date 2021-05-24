package com.owenherbert.cp3406.rocketmaths.game;

/**
* The RocketMaths class represents the game and holds all necessary variables
* required to run the game - such as score, round num, etc.
 *
 * @author Owen Herbert
*/
public class RocketMaths {

    // utility constants
    public static final int MAX_POINTS = 1500; // the amount of points before game over
    public static final int NUM_POINTS_PLANET_ALIGNMENT = 100; // planet alignment points

    // instance variables
    private final GameDifficulty gameDifficulty; // the chosen difficulty of the game

    private String nickname; // nickname of the player
    private Equation roundEquation; // the current equation

    private int roundNumber; // the round the player is on

    private int cpuPoints; // the amount of points the CPU has
    private int userPoints; // the amount of points the player has
    private int tallyCorrect; // the amount of answers the user has gotten correct
    private int tallyIncorrect; // the amount of answers the user has gotten incorrect
    private int roundWorkingNumber; // the working number

    private boolean isPaused; // if the game is paused
    private boolean previousRoundWrong; // if the player got the previous round wrong
    private boolean isWorkingNumberExposed; // if the working number is exposed

    /**
     * Creates a RocketMaths object.
     *
     * @param gameDifficulty the game difficulty
     */
    public RocketMaths(GameDifficulty gameDifficulty) {

        this.gameDifficulty = gameDifficulty;
        reset();
    }

    /**
    * Creates a new equation and increases the round count.
    */
    public void proceedToNextRound() {

        roundEquation = new Equation(gameDifficulty, roundWorkingNumber);
        roundNumber++;
    }

    /**
     * Returns true if the game is over.
     *
     * @return if the game is over.
     */
    public boolean isGameOver() {

        return cpuPoints >= RocketMaths.MAX_POINTS || userPoints >= RocketMaths.MAX_POINTS;
    }

    /**
    * Sets the game to the default state and moves to the first round.
    */
    public void reset() {

        roundNumber = 0;
        roundWorkingNumber = -1;
        previousRoundWrong = false;
        isPaused = true;

        proceedToNextRound();
    }

    /**
     * Increases the tally correct count.
     *
     * @param byAmount the amount to increase by
     */
    public void increaseTallyCorrect(int byAmount) {

        tallyCorrect += byAmount;
    }

    /**
     * Increases the tally incorrect count.
     *
     * @param byAmount the amount to increase by
     */
    public void increaseTallyIncorrect(int byAmount) {

        tallyIncorrect += byAmount;
    }

    /**
     * Returns if the game is on first round.
     */
    public boolean isFirstRound() {

        return roundNumber == 1;
    }

    /**
     * Checks if the provided answer is correct.
     *
     * @param answer the answer to be checked
     * @return if the answer is correct
     */
    public boolean isAnswerCorrect(int answer) {

        return answer == roundEquation.getEquationAnswer();
    }

    /**
     * Increases the users points.
     *
     * @param points the amount to add
     */
    public void addUserPoints(int points) {

        this.userPoints += points;
    }

    /**
     * Decreases the users points.
     *
     * @param points the amount to remove
     */
    public void removeUserPoints(int points) {

        if (this.userPoints - points >= 0) this.userPoints -= points;
    }

    /**
     * Increases the CPUs points.
     *
     * @param points the amount to add
     */
    public void addCpuPoints(int points) {

        this.cpuPoints += points;
    }

    public GameDifficulty getGameDifficulty() {

        return gameDifficulty;
    }

    public String getNickname() {

        return nickname;
    }

    public void setNickname(String nickname) {

        this.nickname = nickname;
    }

    public int getRoundNumber() {

        return roundNumber;
    }

    public int getRoundWorkingNumber() {

        return roundWorkingNumber;
    }

    public void setRoundWorkingNumber(int roundWorkingNumber) {

        this.roundWorkingNumber = roundWorkingNumber;
    }

    public Equation getRoundEquation() {

        return roundEquation;
    }

    public void setRoundNumber(int roundNumber) {

        this.roundNumber = roundNumber;
    }

    public void setRoundEquation(Equation roundEquation) {

        this.roundEquation = roundEquation;
    }

    public boolean isPreviousRoundWrong() {

        return previousRoundWrong;
    }

    public void setPreviousRoundWrong(boolean previousRoundWrong) {

        this.previousRoundWrong = previousRoundWrong;
    }

    public boolean isPaused() {

        return isPaused;
    }

    public void setPaused(boolean paused) {

        isPaused = paused;
    }

    public int getUserPoints() {

        return userPoints;
    }

    public void setUserPoints(int userPoints) {

        this.userPoints = userPoints;
    }

    public int getCpuPoints() {

        return cpuPoints;
    }

    public void setCpuPoints(int cpuPoints) {

        this.cpuPoints = cpuPoints;
    }

    public int getTallyIncorrect() {

        return tallyIncorrect;
    }

    public int getTallyCorrect() {

        return tallyCorrect;
    }

    public boolean isWorkingNumberExposed() {

        return isWorkingNumberExposed;
    }

    public void setWorkingNumberExposed(boolean workingNumberExposed) {

        isWorkingNumberExposed = workingNumberExposed;
    }
}
