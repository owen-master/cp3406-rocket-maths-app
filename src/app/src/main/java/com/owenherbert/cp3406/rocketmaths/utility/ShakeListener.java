package com.owenherbert.cp3406.rocketmaths.utility;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * ShakeListener class.
 *
 * @author Owen Herbert
 * @author Jason McReynolds http://jasonmcreynolds.com/?p=388
 */
public class ShakeListener implements SensorEventListener {

    // utility constants
    private static final float GRAVITY_THRESHOLD = 1.2F;
    private static final int SLOP_TIME_MS = 500;

    // instance variables
    private OnShake onShakeListener;
    private long shakeTimestamp;

    public void setOnShakeListener(OnShake onShakeListener) {

        this.onShakeListener = onShakeListener;
    }

    public interface OnShake {

        void onShake();
    }

    /**
     * Called when the accuracy of the registered sensor has changed. Unlike onSensorChanged(),
     * this is only called when this accuracy value changes.
     *
     * @param sensor the sensor
     * @param accuracy the new accuracy of this sensor, one of SensorManager.SENSOR_STATUS_*
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /**
     * Called when there is a new sensor event. Note that "on changed" is somewhat of a misnomer,
     * as this will also be called if we have a new reading from a sensor with the exact same
     * sensor values (but a newer timestamp).
     *
     * @param event the SensorEvent
     */
    @Override
    public void onSensorChanged(SensorEvent event) {

        if (onShakeListener != null) {

            float x = event.values[0] / SensorManager.GRAVITY_EARTH;
            float y = event.values[1] / SensorManager.GRAVITY_EARTH;
            float z = event.values[2] / SensorManager.GRAVITY_EARTH;

            // calculate gravitational force
            float gravitationalForce = (float) Math.sqrt(x * x + y * y + z * z);

            if (gravitationalForce > GRAVITY_THRESHOLD) {

                final long currentTimeMs = System.currentTimeMillis();

                // if shake occurred too soon ignore it
                if (shakeTimestamp + SLOP_TIME_MS > currentTimeMs) {
                    return;
                }

                shakeTimestamp = currentTimeMs;

                onShakeListener.onShake();
            }
        }
    }
}
