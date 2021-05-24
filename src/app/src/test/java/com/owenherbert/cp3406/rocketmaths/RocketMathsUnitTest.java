package com.owenherbert.cp3406.rocketmaths;

import com.owenherbert.cp3406.rocketmaths.game.Equation;
import com.owenherbert.cp3406.rocketmaths.game.GameDifficulty;
import com.owenherbert.cp3406.rocketmaths.game.RocketMaths;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RocketMathsUnitTest {

    private static final int NUM_TEST_ROUNDS = 100;

    /**
     * Checks if the pause functionality is working as intended.
     */
    @Test
    public void pause() {

        RocketMaths rocketMaths = new RocketMaths(GameDifficulty.EASY);
        rocketMaths.setPaused(true);

        Assert.assertTrue("Game should be paused!", rocketMaths.isPaused());
    }

    /**
     * Checks if the play functionality is working as intended.
     */
    @Test
    public void play() {

        RocketMaths rocketMaths = new RocketMaths(GameDifficulty.EASY);
        rocketMaths.setPaused(false);

        Assert.assertFalse("Game should not be paused!", rocketMaths.isPaused());
    }

    /**
     * Checks if the user points are working as intended.
     */
    @Test
    public void userPoints() {

        final int numPoints = 50;

        RocketMaths rocketMaths = new RocketMaths(GameDifficulty.EASY);
        rocketMaths.setUserPoints(50);

        Assert.assertEquals("User does not have correct amount of points!", numPoints,
                rocketMaths.getUserPoints());
    }

    /**
     * Checks if the CPU points are working as intended.
     */
    @Test
    public void cpuPoints() {

        final int numPoints = 50;

        RocketMaths rocketMaths = new RocketMaths(GameDifficulty.EASY);
        rocketMaths.setCpuPoints(numPoints);

        Assert.assertEquals("CPU does not have correct amount of points!", numPoints,
                rocketMaths.getCpuPoints());
    }

    /**
     * Checks if the user nickname is working as intended.
     */
    @Test
    public void userNickname() {

        final String setNickname = "Johnny";

        RocketMaths rocketMaths = new RocketMaths(GameDifficulty.EASY);
        rocketMaths.setNickname(setNickname);

        Assert.assertEquals("User does not have the correct nickname!", setNickname,
                rocketMaths.getNickname());
    }

    /**
     * Checks if the game equation generation is working as intended.
     */
    @Test
    public void gameEquation() {

        for (GameDifficulty gameDifficulty : GameDifficulty.values()) {

            RocketMaths rocketMaths = new RocketMaths(gameDifficulty);
            Equation equation = rocketMaths.getRoundEquation();
            
            Assert.assertTrue("Game equation is not long enough!",
                    equation.getEquationString().length() > 0);

        }
    }

    /**
     * Checks if the round number is working as intended.
     */
    @Test
    public void roundNumber() {

        RocketMaths rocketMaths = new RocketMaths(GameDifficulty.EASY);
        rocketMaths.proceedToNextRound();

        Assert.assertEquals("Round number is not as expected!", 2,
                rocketMaths.getRoundNumber());

        // test setting of rounds
        final int roundNum = 1;

        rocketMaths.setRoundNumber(roundNum);

        Assert.assertEquals("Round number was not set as expected!", roundNum,
                rocketMaths.getRoundNumber());
    }

    /**
     * Tests that setting the equation is working properly.
     */
    @Test
    public void setEquation() {

        Equation equation = new Equation(GameDifficulty.EASY, 1);

        RocketMaths rocketMaths = new RocketMaths(GameDifficulty.EASY);
        rocketMaths.setRoundEquation(equation);

        Assert.assertSame("Equation was not set to the game round!",
                rocketMaths.getRoundEquation(), equation);
    }

    /**
     * Tests that game functionality is working properly.
     */
    @Test
    public void testGameMechanics() {

        final String testUserNick = "Foo";

        // run a test for each game difficulty
        for (GameDifficulty gameDifficulty : GameDifficulty.values()) {

            RocketMaths rocketMaths = new RocketMaths(gameDifficulty);

            rocketMaths.setNickname(testUserNick);

            // loop number of testing rounds
            for (int i = 0; i < NUM_TEST_ROUNDS; i++) {

                final int expectedRoundNumber = i + 1;

                // check if the round number is as expected
                assertEquals("Round number not as expected!", expectedRoundNumber,
                        rocketMaths.getRoundNumber());

                // check if the equation is as expected - it contains allowed operators
                char[] allowedOperators = new char[gameDifficulty.getOperationTypes().length];

                for (int j = 0; j < gameDifficulty.getOperationTypes().length; j++) {

                    allowedOperators[j] =
                            rocketMaths.getGameDifficulty().getOperationTypes()[j].getCharacter();
                }

                char[] equationArray =
                        rocketMaths.getRoundEquation().getEquationString().toCharArray();

                for (char c : equationArray) {

                    // only match characters other than question marks, spaces and digits - should
                    // result in only operators remaining
                    if (c != '?' && !Character.isSpaceChar(c) && !Character.isDigit(c)) {

                        boolean isOperatorValid = false;

                        // check if character is an allowed operator
                        for (char allowedOperator : allowedOperators) {

                            if (c == allowedOperator) {

                                isOperatorValid = true;
                                break;
                            }
                        }

                        assertTrue("Invalid operator included in equation!", isOperatorValid);
                    }
                }

                // check if the correct equation answer is in possible answers
                ArrayList<Integer> possibleAnswers =
                        rocketMaths.getRoundEquation().getPossibleEquationAnswers();

                int correctAnswer = rocketMaths.getRoundEquation().getEquationAnswer();

                assertTrue("Correct answer is not in possible answer list!",
                        possibleAnswers.contains(correctAnswer));

                rocketMaths.proceedToNextRound();
            }
        }
    }
}

