package sheridan.romeroad.usersideapp.viewmodels

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingEvent
import com.google.android.gms.location.GeofencingEvent.*
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class GeofenceViewModel : ViewModel() {

    private var googleMap: GoogleMap? = null
    private var geofencingClient: GeofencingClient? = null

    fun initializeMap(map: GoogleMap) {
        this.googleMap = map
    }

    suspend fun searchAddress(context: Context, address: String) {
        val apiKey = "AIzaSyCGP1r4R5gzEMNt9zBJOPGEd3WRcf1LnGo" // Replace with your actual API key
        val formattedAddress = address.replace(" ", "+")
        val url = "https://maps.googleapis.com/maps/api/geocode/json?address=$formattedAddress&key=$apiKey"

        withContext(Dispatchers.IO) {
            try {
                val connection = URL(url).openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connect()

                val response = connection.inputStream.bufferedReader().use { it.readText() }
                val jsonObject = JSONObject(response)
                val results = jsonObject.getJSONArray("results")

                if (results.length() > 0) {
                    val location =
                        results.getJSONObject(0).getJSONObject("geometry").getJSONObject("location")
                    val latLng = LatLng(location.getDouble("lat"), location.getDouble("lng"))

                    withContext(Dispatchers.Main) {
                        googleMap?.clear()
                        googleMap?.addMarker(MarkerOptions().position(latLng).title("Selected Location"))
                        googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                    }
                } else {
                    Log.e("GeofenceViewModel", "No location found")
                }
            } catch (e: Exception) {
                Log.e("GeofenceViewModel", "Geocoder API Error: ${e.message}")
            }
        }
    }


    @SuppressLint("MissingPermission")
    fun setGeofence(latLng: LatLng) {
        googleMap?.clear()
        googleMap?.addMarker(MarkerOptions().position(latLng).title("Geofence Center"))

        // Draw geofence circle
        googleMap?.addCircle(
            CircleOptions()
                .center(latLng)
                .radius(100.0) // 100 meters radius
                .strokeWidth(2f)
                .strokeColor(0xFF6200EE.toInt())
                .fillColor(0x226200EE)
        )
    }

    fun initializeGeofencing(context: Context) {
        geofencingClient = LocationServices.getGeofencingClient(context)
    }

    @SuppressLint("MissingPermission")
    fun addGeofence(context: Context, latLng: LatLng) {
        val geofence = Geofence.Builder()
            .setRequestId("GEOFENCE_ID")
            .setCircularRegion(latLng.latitude, latLng.longitude, 100f) // 100m radius
            .setExpirationDuration(60 * 60 * 1000) // 1 hour
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_EXIT)
            .build()

        val request = GeofencingRequest.Builder()
            .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            .addGeofence(geofence)
            .build()

        geofencingClient?.addGeofences(request, getGeofencePendingIntent(context))
    }

    fun getGeofencePendingIntent(context: Context): PendingIntent {
        val intent = Intent(context, GeofenceBroadcastReceiver::class.java)
        return PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }
}

class GeofenceBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent == null) return

        val geofencingEvent = fromIntent(intent)
        if (geofencingEvent != null) {
            if (geofencingEvent.hasError()) {
                Log.e("Geofence", "Geofence error: ${geofencingEvent.errorCode}")
                return
            }
        }

        if (geofencingEvent != null) {
            when (geofencingEvent.geofenceTransition) {
                Geofence.GEOFENCE_TRANSITION_ENTER -> Log.d("Geofence", "Entered the geofence!")
                Geofence.GEOFENCE_TRANSITION_EXIT -> Log.d("Geofence", "Exited the geofence!")
                else -> Log.d("Geofence", "Unknown transition")
            }
        }
    }
}
