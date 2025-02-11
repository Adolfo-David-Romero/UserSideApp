package sheridan.romeroad.usersideapp.ui.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.TileOverlayOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapEffect
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.heatmaps.HeatmapTileProvider
import sheridan.romeroad.usersideapp.viewmodels.MapViewModel

/**
 * Student ID: 991555778
 * UserSideApp
 * created by davidromero
 * on 2025-02-11
 **/

//43.65526458403934, -79.73853015042334
@OptIn(MapsComposeExperimentalApi::class)
@Composable
fun MapScreen(viewModel: MapViewModel = viewModel()) {
    val locations by viewModel.locations.collectAsState()

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(43.66023, -79.73777),
            10f
        )
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        MapEffect(locations) { map ->
            if (locations.isNotEmpty()) {
                val heatmapProvider = HeatmapTileProvider.Builder()
                    .data(locations)
                    .build()
                map.addTileOverlay(TileOverlayOptions().tileProvider(heatmapProvider))
            }
        }
    }

}