package com.owenherbert.cp3406.rocketmaths.utility;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

/**
 * The TwitterManager class allows easy programmatic access to the Twitter4j API.
 *
 * @author Owen Herbert
 */
public class TwitterManager {

    /**
     * Tweets the provided message.
     *
     * @param message the message to tweet
     */
    public static void tweet(String message) {

        // perform action on new thread - send tweet
        ThreadRunner.run(() -> {

            Twitter twitter = TwitterFactory.getSingleton();

            try {
                twitter.updateStatus(message);
            } catch (TwitterException err) {
                err.printStackTrace();
            }
        });
    }
}
