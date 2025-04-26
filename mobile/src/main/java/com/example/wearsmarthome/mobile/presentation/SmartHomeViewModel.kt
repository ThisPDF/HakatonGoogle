package com.example.wearsmarthome.mobile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wearsmarthome.mobile.data.SmartHomeRepository
import com.example.wearsmarthome.mobile.data.WearableRepository
import com.example.wearsmarthome.shared.data.LocationData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SmartHomeUiState(
    val isHome: Boolean = false,
    val lightsOn: Boolean = false,
    val thermostatOn: Boolean = false,
    val temperature: Int = 72,
    val lastLocationUpdate: LocationData? = null
)

@HiltViewModel
class SmartHomeViewModel @Inject constructor(
    private val wearableRepository: WearableRepository,
    private val smartHomeRepository: SmartHomeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SmartHomeUiState())
    val uiState: StateFlow<SmartHomeUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                wearableRepository.locationData,
                smartHomeRepository.smartHomeState
            ) { locationData, smartHomeState ->
                SmartHomeUiState(
                    isHome = smartHomeState.isHome,
                    lightsOn = smartHomeState.lightsOn,
                    thermostatOn = smartHomeState.thermostatOn,
                    temperature = smartHomeState.temperature,
                    lastLocationUpdate = locationData
                )
            }.collect { state ->
                _uiState.update { state }
            }
        }
    }
    
    fun toggleLights() {
        smartHomeRepository.toggleLights()
    }
    
    fun toggleThermostat() {
        smartHomeRepository.toggleThermostat()
    }
    
    fun setTemperature(temperature: Int) {
        smartHomeRepository.setTemperature(temperature)
    }
}
