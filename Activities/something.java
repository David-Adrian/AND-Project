package com.example.andproject;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;



public class something extends AppCompatActivity implements SensorEventListener{

    SensorManager sensorManager;
    Sensor stepSensor;
    private Sensor senAccelerometer;

    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 600;

    Button reset;

    TextView numberSteps, valueX,valueY, valueZ, data ;
    int counter = 0;
    private long steps = 0;

//    boolean running = false;

    private FirebaseAuth.AuthStateListener authState;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.something);

        data = (TextView) findViewById(R.id.textView2);

        valueX = (TextView) findViewById(R.id.textView3);
        valueY = (TextView) findViewById(R.id.textView4);
        valueZ = (TextView) findViewById(R.id.textView5);

        reset = (Button) findViewById(R.id.button2);
        numberSteps = (TextView) findViewById(R.id.textView6);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);


        if(senAccelerometer != null){
            sensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_UI);
        }else{
            Toast.makeText(something.this, "Sensor not found", Toast.LENGTH_SHORT).show();
        }

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = 0;
            }
        });

    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        running = true;
//        Sensor countSensor  = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//        if(countSensor != null){
//            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
//        }else{
//            Toast.makeText(something.this, "Sensor not found", Toast.LENGTH_SHORT).show();
//        }
//    }


    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {

        super.onResume();

        sensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this, stepSensor);
    }

    @Override
    public void onSensorChanged(final SensorEvent sensorEvent) {



//        getSteps(sensorEvent);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
               getSteps(sensorEvent);
            }
        }, 1900);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void getSteps(SensorEvent sensorEvent){


        Sensor mySensor = sensorEvent.sensor;

        if(mySensor.getType() == Sensor.TYPE_ACCELEROMETER){

            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            double sum = Math.sqrt((x * x) + (y * y) + (z * z));

//            (z * z)

            int finals = (int) sum ;

            data.setText(String.valueOf(sum));
            valueX.setText("X: " + x);
            valueY.setText("Y: " + y);
            valueZ.setText("Z: " + z);



            numberSteps.setText(String.valueOf(counter));
            if( sum >= 10.6) {
                counter++;
            }



        }
    }

}