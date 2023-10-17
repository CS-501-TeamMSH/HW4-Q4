package com.example.hw4_q4

import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class SouthActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var sensorRegistered = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_south)

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
            val movementx = event.values[0]
            val movementy = event.values[1]
            val flingThreshold = 10

            if (movementy > flingThreshold) {
                openActivity(MainActivity::class.java)
            } else if (movementx > flingThreshold) {
                openActivity(EastActivity::class.java)
            } else if (movementx < -flingThreshold) {
                openActivity(WestActivity::class.java)
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