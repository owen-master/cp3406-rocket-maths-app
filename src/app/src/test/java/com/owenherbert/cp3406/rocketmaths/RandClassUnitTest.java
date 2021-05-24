package com.owenherbert.cp3406.rocketmaths;

import com.owenherbert.cp3406.rocketmaths.utility.Rand;

import org.junit.Test;

import static org.junit.Assert.*;

public class RandClassUnitTest {

    private static final int NUM_TESTS = 10000;

    /**
     * Checks if the nextInt function in the Rand class is working properly.
     */
    @Test
    public void nextInt() {

        for (int i = 0; i < NUM_TESTS; i++) {

            int maxBound = i + 1;

            int nextInt = Rand.nextInt(maxBound);
            assertTrue("Integer was larger than max bound!", nextInt < maxBound);
        }
    }

    /**
     * Checks if the between function in the Rand class is working properly.
     */
    @Test
    public void between() {

        for (int i = 0; i < NUM_TESTS; i++) {

            int max = Rand.nextInt(Integer.MAX_VALUE);
            int min = Rand.nextInt(1);

            int randomBetween = Rand.between(min, max);

            assertTrue("Integer not between min and max range!",
                    randomBetween < max && randomBetween > min);
        }
    }
}