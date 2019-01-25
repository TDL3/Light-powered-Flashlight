package com.tdl3.light_powered_flashlight;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener{
    private SensorManager mSensorManager;
    private Sensor mLight;
    private TextView status;
    private ImageView flashlight;
    FlashManager flashManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        flashlight = findViewById(R.id.imageView_Flashlight);
        status = findViewById(R.id.textView_status);
        flashManager = new FlashManager(this, status);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.i("Sensor", String.valueOf(event.values[0]));
        if (event.values[0] < 70) {
            flashlight.setImageResource(R.drawable.flashlight_off);
            flashManager.toggleFlashLight(false);
            status.setText(R.string.low_light);
        } else {
            flashlight.setImageResource(R.drawable.flashlight_on);
            flashManager.toggleFlashLight(true);
            status.setText(R.string.high_light);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mLight, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
}
