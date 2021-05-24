package com.owenherbert.cp3406.rocketmaths.game;

import com.owenherbert.cp3406.rocketmaths.utility.Rand;

import java.util.ArrayList;
import java.util.Collections;

import bsh.EvalError;
import bsh.Interpreter;

/**
* The Equation class represents an equation as part of a RocketMath round.
 *
 * @author Owen Herbert
*/
public class Equation {

    // instance variables
    private String equationString; // the string in which the equation is to be formed
    private final int equationAnswer; // the answer to the equation string
    private final GameDifficulty gameDifficulty; // the game difficulty

    /**
     * Creates an Equation object.
     *
     * @param gameDifficulty the game difficulty
     * @param workingNumber the working number
     */
    public Equation(GameDifficulty gameDifficulty, int workingNumber) {

        this.gameDifficulty = gameDifficulty;

        // get operation types from the game difficulty
       OperationType[] operationTypes = gameDifficulty.getOperationTypes();

        // count how many operations there will be
        ArrayList<String> operationSymbols = new ArrayList<>();
        
        // add required operation symbols to array list
        for (OperationType operationType : operationTypes) {

            operationSymbols.add(String.valueOf(operationType.getCharacter()));
        }

        // operation symbols need to be randomised between game rounds
        Collections.shuffle(operationSymbols);

        ArrayList<Integer> randomNumbers = new ArrayList<>();
        int numbersRequired;

        // generate random numbers to use in the equation creation
        numbersRequired = gameDifficulty.getLengthOfEquation();

        // if there is a working number, add it to the list of random numbers
        if (workingNumber != -1) {
            numbersRequired = gameDifficulty.getLengthOfEquation() - 1;
            randomNumbers.add(workingNumber);
        }

        // generate the remaining random numbers required
        for (int i = 0; i < numbersRequired; i++) {

            // create a random number within the difficulty bounds
            int randNum = Rand.between(gameDifficulty.getMinimumNumber(),
                    gameDifficulty.getMaximumNumber());

            randomNumbers.add(randNum);
        }

        // create equation string
        equationString = "";

        int idx = 0;
        while (!randomNumbers.isEmpty()) {

            equationString = String.format("%s%s", equationString, " " + randomNumbers.get(0));

            if (idx < (gameDifficulty.getLengthOfEquation() - 1)) {
                equationString = String.format("%s%s", equationString, " " + operationSymbols.get(idx++));
            }

            randomNumbers.remove(0);
        }

        // trim the equation string
        equationString = equationString.trim();

        // get answer for the create equation string
        equationAnswer = evaluateAnswerFromString(equationString);
    }

    /**
     * Evaluates the answer to the equation string and returns the value.
     *
     * @return the answer to the equation string
     */
    private static int evaluateAnswerFromString(String equationString) {

        int evaluatedAnswer = 0;

        Interpreter interpreter = new Interpreter();

        try {

            evaluatedAnswer =
                    Math.round(Integer.parseInt(interpreter.eval(equationString).toString()));

        } catch (EvalError evalError) {

            evalError.printStackTrace();
        }

        return evaluatedAnswer;
    }

    /**
    * Get possible answers to the equation, only one of these answers will
    * be correct. The rest are close to the answer but are incorrect.
    * 
    * @return An ArrayList of possible equation answers
    */
    public ArrayList<Integer> getPossibleEquationAnswers() {

        // create a new array list
        ArrayList<Integer> possibleEquationAnswers = new ArrayList<>();

        // add correct answer to array list
        possibleEquationAnswers.add(Math.round(equationAnswer));

        // add incorrect answers to array list
        for (int i = 0; i < 4; i++) {

            int randomNumber;

            // make sure answers added to list are not repeated
            do {
                if (i % 2 == 0) {
                    randomNumber = equationAnswer - Rand.nextInt(gameDifficulty.getMaximumNumber());
                } else {
                    randomNumber = equationAnswer + Rand.nextInt(gameDifficulty.getMaximumNumber());
                }
            } while (possibleEquationAnswers.contains(randomNumber));

            possibleEquationAnswers.add(randomNumber);
        }

        // shuffle the order of possible equation answers in the array list
        Collections.shuffle(possibleEquationAnswers);

        return possibleEquationAnswers;
    }

    /**
     * Gets the equation string without the working number. The working
     * number is instead replaced with a question mark.
     *
     * @return The equation string without working number
     */
    public String getEquationStringWithoutWorkingNumber() {

        String[] equationStringArray = this.equationString.split("");

        int indexOfFirstSpace = -1;
        for (int i = 0; i < equationStringArray.length; i++) {
            if (equationStringArray[i].equals(" ")) {
                indexOfFirstSpace = i;
                break;
            }
        }

        return "?" + this.equationString.substring(indexOfFirstSpace);
    }

    /**
     * Get the equation answer.
     * @return the equation answer
     */
    public int getEquationAnswer() {
        return equationAnswer;
    }

    /**
     * Get the equation string.
     * @return the equation string
     */
    public String getEquationString() {
        return equationString;
    }
}
