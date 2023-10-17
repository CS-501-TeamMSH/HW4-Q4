package com.example.hw4_q4

import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity(), SensorEventListener {

    // Note: Testing with threshold of 3.0 because my
    // virtual device in device pose only allows for that

    private lateinit var sensorManager: SensorManager
    private var sensorRegistered = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!sensorRegistered) {
            setupSensor()
            sensorRegistered = true
        }
    }

    private fun setupSensor() {
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        if (accelerometer != null) {
            Log.e("Sensor", "Accelerometer available")
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI)
        } else {
            Log.e("Sensor", "Accelerometer not available")
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            if (event.values.size >= 2) {
            val movementx = event.values[0]
            val movementy = event.values[1]
            val flingThreshold = 10

            if (movementy > flingThreshold) {
                openActivity(NorthActivity::class.java)
            } else if (movementy < -flingThreshold) {
                openActivity(SouthActivity::class.java)
            } else if (movementx > flingThreshold) {
                openActivity(EastActivity::class.java)
            } else if (movementx < -flingThreshold) {
                openActivity(WestActivity::class.java)
            }

        } else {
            Log.e("Sensor", "Invalid accelerometer data format")
        }

        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return
    }

    private fun openActivity(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
    }




}