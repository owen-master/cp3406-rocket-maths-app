package com.owenherbert.cp3406.rocketmaths.utility;

/**
 * The ThreadRunner class allows runnables to be added to a thread and started easily.
 *
 * @author Owen Herbert
 */
public class ThreadRunner {

    /**
     * Runs the provided runnable inside a new thread.
     *
     * @param runnable the Runnable
     */
    public static void run(Runnable runnable) {

        // create and start a new thread with the runnable
        new Thread(runnable).start();
    }
}
