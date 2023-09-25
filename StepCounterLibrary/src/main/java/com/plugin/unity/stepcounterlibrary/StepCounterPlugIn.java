package com.plugin.unity.stepcounterlibrary;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Toast;

public class StepCounterPlugIn implements SensorEventListener {

    // Management
    private static Activity unityActivity;
    private SensorManager sensorManager;

    // Step counting
    private boolean counting = false;
    private float previousSteps = 0f;
    private int currentSteps = 0;

    public void InitializeStepCounter(Activity assignActivity){
        unityActivity = assignActivity;
        sensorManager = (SensorManager) assignActivity.getSystemService(Context.SENSOR_SERVICE);
    } // InitializeStepCounter

    public void StartCounting(){
        currentSteps = 0;
        previousSteps = 0f;
        counting = true;
        Sensor stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        if (stepSensor == null) {
            Toast.makeText(unityActivity,
                    "StepSensor not supported on this device.",
                    Toast.LENGTH_LONG).show();
        } // if
        else {
            sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_GAME);
        } // else
    } // StartCounting

    public int StopCounting(){
        counting = false;
        return currentSteps;
    } // StopCounting

    public int GetCurrentSteps(){
        return currentSteps;
    } // GetCurrentSteps

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(counting){
            float totalSteps = sensorEvent.values[0];
            currentSteps = (int)(totalSteps - previousSteps);
        } // if
    } // onSensorChanged

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {} // onAccuracyChanged
}
