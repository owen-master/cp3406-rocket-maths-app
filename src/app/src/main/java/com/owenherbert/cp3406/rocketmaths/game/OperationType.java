package com.owenherbert.cp3406.rocketmaths.game;

/**
* The OperationType enum represents a math operation type that might be included in a particular
* GameDifficulty.
*
* @author Owen Herbert
*/
public enum OperationType {

    ADDITION('+'),
    SUBTRACTION('-'),
    MULTIPLICATION('*'),
    DIVISION('/');

    // instance variables
    final char character;

    /**
     * Creates an OperationType.
     *
     * @param character the character that represents the operation type
     */
    OperationType (char character) {

        this.character = character;
    }

    /**
     * Get the character.
     * @return the character
     */
    public char getCharacter() {

        return this.character;
    }
}