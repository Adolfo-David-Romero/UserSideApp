package sheridan.romeroad.usersideapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import sheridan.romeroad.usersideapp.data.LocationData
import sheridan.romeroad.usersideapp.data.MapRepository

/**
 * Student ID: 991555778
 * UserSideApp
 * created by davidromero
 * on 2025-02-11
 **/
class MapViewModel : ViewModel() {

    private val repository = MapRepository()

    private val _locations = MutableStateFlow<List<LatLng>>(emptyList())
    val locations: StateFlow<List<LatLng>> = _locations.asStateFlow()

    init {
        observeHeatmapData()
    }

    private fun observeHeatmapData() {
        repository.getHeatmapCoordinatesRealTime { fetchedLocations ->
            _locations.value = fetchedLocations.map { LatLng(it.latitude, it.longitude) }
        }
    }

}