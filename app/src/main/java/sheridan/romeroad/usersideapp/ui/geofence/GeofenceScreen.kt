package sheridan.romeroad.usersideapp.ui.geofence

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.os.Looper
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import kotlinx.coroutines.launch
import sheridan.romeroad.usersideapp.viewmodels.GeofenceViewModel
import java.util.*

@Composable
fun GeofenceScreen(viewModel: GeofenceViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val context = LocalContext.current
    val mapState = remember { mutableStateOf<GoogleMap?>(null) }
    val searchQuery = remember { mutableStateOf("") }
    val markerPosition = remember { mutableStateOf(LatLng(0.0, 0.0)) }
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(
            value = searchQuery.value,
            onValueChange = { searchQuery.value = it },
            label = { Text("Enter Address") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                coroutineScope.launch {
                    viewModel.searchAddress(context, searchQuery.value)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Search Address")
        }

        Spacer(modifier = Modifier.height(10.dp))

        GoogleMapView(
            onMapReady = { googleMap ->
                mapState.value = googleMap
                viewModel.initializeMap(googleMap)
            },
            onMapClick = { latLng ->
                markerPosition.value = latLng
                viewModel.setGeofence(latLng)
            }
        )
    }
}

@Composable
fun GoogleMapView(onMapReady: (GoogleMap) -> Unit, onMapClick: (LatLng) -> Unit) {
    AndroidView(
        factory = { context ->
            val mapView = com.google.android.gms.maps.MapView(context)
            mapView.onCreate(null)
            mapView.onResume()
            mapView.getMapAsync { googleMap ->
                googleMap.uiSettings.isZoomControlsEnabled = true
                onMapReady(googleMap)
                googleMap.setOnMapClickListener(onMapClick)
            }
            mapView
        },
        modifier = Modifier.fillMaxSize()
    )
}