package com.example.wearsmarthome.wear.presentation

import android.app.Application
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wearsmarthome.shared.data.LocationData
import com.example.wearsmarthome.wear.data.LocationRepository
import com.example.wearsmarthome.wear.service.LocationTrackingService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val isTracking: Boolean = false,
    val lastLocation: LocationData? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val application: Application,
    private val locationRepository: LocationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        getCurrentLocation()
    }

    fun startTracking() {
        val intent = Intent(application, LocationTrackingService::class.java)
        application.startService(intent)
        _uiState.update { it.copy(isTracking = true) }
    }

    fun stopTracking() {
        val intent = Intent(application, LocationTrackingService::class.java)
        application.stopService(intent)
        _uiState.update { it.copy(isTracking = false) }
    }

    private fun getCurrentLocation() {
        viewModelScope.launch {
            val location = locationRepository.getCurrentLocation()
            location?.let {
                _uiState.update { state -> state.copy(lastLocation = location) }
            }
        }
    }
}
