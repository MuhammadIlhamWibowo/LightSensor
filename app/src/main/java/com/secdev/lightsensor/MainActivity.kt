package com.secdev.lightsensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MainActivity : AppCompatActivity(), SensorEventListener {
    private var sensor: Sensor? = null
    private var sensorManager: SensorManager? = null
    private var isRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_LIGHT)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }


    override fun onSensorChanged(event: SensorEvent?) {
        if (event!!.values[0] > 40 && !isRunning) {
            isRunning = true
            // play music
            try {
//                val mediaPlayer = MediaPlayer.create(applicationContext, R.raw.alarm_tone)
                val mediaPlayer = MediaPlayer()
                mediaPlayer.setDataSource("http://server6.mp3quran.net/thubti/001.mp3")
                mediaPlayer.prepare()
                mediaPlayer.start()
            } catch (e: Exception) {
                Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()

        sensorManager!!.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()

        sensorManager!!.unregisterListener(this)
    }
}
