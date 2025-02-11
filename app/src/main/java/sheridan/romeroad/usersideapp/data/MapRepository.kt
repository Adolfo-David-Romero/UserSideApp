package sheridan.romeroad.usersideapp.data


import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.tasks.await

/**
 * Student ID: 991555778
 * UserSideApp
 * created by davidromero
 * on 2025-02-11
 **/


data class LocationData(val latitude: Double = 0.0, val longitude: Double = 0.0)

class MapRepository {
    private val firestore = FirebaseFirestore.getInstance()

    // Real-time listener with manual type conversion
    fun getHeatmapCoordinatesRealTime(callback: (List<LocationData>) -> Unit) {
        firestore.collection("heatmapLocations")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val locations = snapshot.documents.mapNotNull { doc ->
                        try {
                            val lat = doc.get("latitude")?.toString()?.toDoubleOrNull()
                            val lng = doc.get("longitude")?.toString()?.toDoubleOrNull()
                            if (lat != null && lng != null) {
                                LocationData(lat, lng)
                            } else {
                                null
                            }
                        } catch (e: Exception) {
                            null
                        }
                    }
                    callback(locations)
                }
            }
    }
}