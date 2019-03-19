package com.example.shivansh.compass

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.hardware.SensorManager
import android.widget.ImageView
import android.view.animation.Animation
import android.view.animation.RotateAnimation




class MainActivity : AppCompatActivity(),SensorEventListener {
    private var sensorManager: SensorManager? = null
    private var compass: Sensor? = null
    private var image: ImageView? = null
    private var compassAngle: TextView? = null
    private var currentDegree = 0f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        image = findViewById(R.id.imageViewCompass)
        compassAngle = findViewById(R.id.angle)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager?
        compass = sensorManager!!.getDefaultSensor(Sensor.TYPE_LIGHT)
        if(compass != null){
            sensorManager!!.registerListener(this, compass, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val degree = Math.round(event?.values?.get(0)!!)
        compassAngle?.setText("Heading: " + java.lang.Float.toString(degree.toFloat()) + " degrees")

        val ra = RotateAnimation(currentDegree, (-degree).toFloat(), Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)

        ra.duration = 210

        ra.fillAfter = true

        image!!.startAnimation(ra)
        currentDegree = (-degree).toFloat()

    }
    override fun onPause() {
        super.onPause()
        sensorManager!!.unregisterListener(this)
    }

    override fun onResume() {
        super.onResume()
        sensorManager!!.registerListener(this, compass, SensorManager.SENSOR_DELAY_NORMAL)
    }
}
