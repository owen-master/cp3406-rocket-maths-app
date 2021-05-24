package com.owenherbert.cp3406.rocketmaths.utility;

import java.util.Random;

/**
 * The Rand class allows random numbers to be easily generated.
 *
 * @author Owen Herbert
 */
public class Rand {

    private static final Random random = new Random();

    /**
     * Returns a random number between zero and integer
     *
     * @param integer the maximum number that can be returned
     * @return the random integer
     */
    public static int nextInt(int integer) {

        return random.nextInt(integer);
    }

    /**
     * Creates a random integer between the min and max bounds inclusive
     *
     * @return the random int
     */
    public static int between(int min, int max) {

        return (int) (Math.random() * (max-min)) + min;
    }
}
