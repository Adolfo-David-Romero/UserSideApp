package sheridan.romeroad.usersideapp.services

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*
/**
 * Student ID: 991555778
 * UserSideApp
 * created by davidromero
 * on 2024-11-28
 **/


class FallDetectionService : Service(), SensorEventListener, LocationListener {

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private lateinit var locationManager: LocationManager
    private val db = FirebaseFirestore.getInstance()

    private var lastAcceleration: Float = 0f
    private var currentAcceleration: Float = 0f
    private var shake: Float = 0f
    private var lastFallTime: Long = 0

    override fun onCreate() {
        super.onCreate()

        // Initialize SensorManager and Accelerometer
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        accelerometer?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }

        // Initialize LocationManager
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager

        if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == android.content.pm.PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, 0f, this)
        } else {
            Log.e("FallDetectionService", "Location permissions not granted.")
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null && event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            lastAcceleration = currentAcceleration
            currentAcceleration = Math.sqrt((x * x + y * y + z * z).toDouble()).toFloat()
            val delta = currentAcceleration - lastAcceleration
            shake = shake * 0.9f + delta

            if (shake > 12) { // Threshold for fall detection
                onFallDetected()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun onFallDetected() {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastFallTime < 5000) return // 5-second cooldown
        lastFallTime = currentTime

        val location: Location? = try {
            locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        } catch (e: SecurityException) {
            e.printStackTrace()
            null
        }

        val time = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())

        val alertData = mapOf(
            "time" to time,
            "latitude" to (location?.latitude?.toString() ?: "Unknown"),
            "longitude" to (location?.longitude?.toString() ?: "Unknown"),
            "message" to "Fall detected for the user."
        )

        db.collection("alerts")
            .add(alertData)
            .addOnSuccessListener {
                Log.d("FallDetectionService", "Fall alert sent successfully")
            }
            .addOnFailureListener { e ->
                Log.e("FallDetectionService", "Failed to send fall alert", e)
            }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onDestroy() {
        super.onDestroy()
        sensorManager.unregisterListener(this)
        locationManager.removeUpdates(this)
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onLocationChanged(location: Location) {}

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}

    override fun onProviderEnabled(provider: String) {}

    override fun onProviderDisabled(provider: String) {}
}