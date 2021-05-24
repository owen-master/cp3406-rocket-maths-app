package com.owenherbert.cp3406.rocketmaths.sound;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import java.util.ArrayList;

/**
* The SoundManager class is used to play sounds easily.
 *
 * @author Owen Herbert
*/
public class SoundManager {

    // utility constants
    private static final int MAX_STREAMS = 4; // amount of streams that can play at once
    private static final String ANDROID_RES_FOLD_PREFIX = "android.resource://"; // resource path
    private static final float VOL_MUSIC = .5f; // volume for music
    private static final float VOL_SOUND = 1f; // volume for sounds

    private final Context context; // the activity context

    private final ArrayList<MediaPlayerStream> mediaPlayerStreamPool;

    public SoundManager(Context context) {

        this.context = context;
        mediaPlayerStreamPool = new ArrayList<>();

        // create media player objects
        for (int i = 0; i < MAX_STREAMS; i++) {

            mediaPlayerStreamPool.add(buildMediaPlayer());
        }
    }

    /**
     * Builds and returns a MediaPlayerStream object. A built MediaPlayerStream object will start
     * playing when prepared and recycle on sound completion.
     *
     * @return A ready to use MediaPlayerStream object
     */
    private MediaPlayerStream buildMediaPlayer() {

        MediaPlayerStream mediaPlayerStream;

        mediaPlayerStream = new MediaPlayerStream();
        mediaPlayerStream.setOnPreparedListener(MediaPlayer::start);
        mediaPlayerStream.setOnCompletionListener(mediaPlayer -> mediaPlayerStream.recycle());

        return mediaPlayerStream;
    }

    /**
     * Requests a MediaPlayerStream object from the MediaPlayerStream pool. If there are not
     * any available MediaPlayerStreams that are not in use, null will be returned.
     *
     * @return MediaPlayerStream if there is an available MediaPlayerStream
     * @code null if there are no available MediaPlayerStreams in the pool
     */
    private MediaPlayerStream requestMediaPlayerStream() {

        for (MediaPlayerStream mediaPlayerStream: mediaPlayerStreamPool) {

            if (!mediaPlayerStream.isInUse()) return mediaPlayerStream;
        }

        return null;
    }

    /**
    * Stops all of the playing MediaPlayerStreams.
     */
    public void stopAll() {

        for (MediaPlayerStream mediaPlayerStream : mediaPlayerStreamPool) {

            if (mediaPlayerStream.isInUse()) mediaPlayerStream.recycle();
        }
    }

    /**
     * Plays the specified GameSound.
     *
     * @param gameSound the sound to be played
     */
    public void play (GameSound gameSound) {

        MediaPlayerStream mediaPlayerStream = requestMediaPlayerStream();

        // if there are no available streams available then music will sound will be ignored
        if (mediaPlayerStream != null) {

            mediaPlayerStream.setInUse(true);
            mediaPlayerStream.setGameSound(gameSound);

            // lower volume if it is music
            float volume = (gameSound.isMusic()) ? VOL_MUSIC : VOL_SOUND;
            mediaPlayerStream.setVolume(volume, volume);

            try {

                mediaPlayerStream.setDataSource(context, makeUri(gameSound.getResourceId()));
                mediaPlayerStream.prepare();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Players the specified GameSound if it is not already playing.
     *
     * @param gameSound the sound to be played
     */
    public void playIfNotPlaying(GameSound gameSound) {

        // check if sound is playing
        for (MediaPlayerStream mps : mediaPlayerStreamPool) {

            if (mps.getGameSound() == gameSound) return;
        }

        play(gameSound);
    }

    /**
     * Play paused music in the MediaPlayerStream sound pool.
     */
    public void playPausedMusic() {

        for (MediaPlayerStream mps : mediaPlayerStreamPool) {

            if (mps.isInUse() && mps.getGameSound().isMusic() && !mps.isPlaying()) {

                mps.start();
            }
        }
    }

    /**
     * Pause playing music in the MediaPlayerStream sound pool.
     */
    public void pausePlayingMusic() {

        for (MediaPlayerStream mps : mediaPlayerStreamPool) {

            if (mps.isInUse() && mps.getGameSound().isMusic() && mps.isPlaying()) {

                mps.pause();
            }
        }
    }

    /**
     * Creates and returns a Uri for the specified resource id.
     *
     * @param resourceID the resource ID
     * @return the created Uri
     */
    private Uri makeUri(int resourceID) {

        return Uri.parse(ANDROID_RES_FOLD_PREFIX + context.getPackageName() + "/" + resourceID);
    }
}
