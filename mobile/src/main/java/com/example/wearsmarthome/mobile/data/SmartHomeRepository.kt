package com.example.wearsmarthome.mobile.data

import com.example.wearsmarthome.shared.data.LocationData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class SmartHomeState(
    val isHome: Boolean = false,
    val lightsOn: Boolean = false,
    val thermostatOn: Boolean = false,
    val temperature: Int = 72
)

interface SmartHomeRepository {
    val smartHomeState: StateFlow<SmartHomeState>
    fun updateHomeStatus(isHome: Boolean)
    fun toggleLights()
    fun toggleThermostat()
    fun setTemperature(temperature: Int)
    fun processLocationUpdate(locationData: LocationData)
}

class SmartHomeRepositoryImpl @Inject constructor() : SmartHomeRepository {

    private val _smartHomeState = MutableStateFlow(SmartHomeState())
    override val smartHomeState: StateFlow<SmartHomeState> = _smartHomeState.asStateFlow()
    
    // Home location (would be set by user in real app)
    private val homeLatitude = 37.4220
    private val homeLongitude = -122.0841
    private val homeRadius = 0.001 // Approximately 100 meters
    
    override fun updateHomeStatus(isHome: Boolean) {
        _smartHomeState.update { it.copy(isHome = isHome) }
        
        // Auto-adjust home settings when arriving
        if (isHome && !_smartHomeState.value.isHome) {
            _smartHomeState.update { 
                it.copy(
                    lightsOn = true,
                    thermostatOn = true
                )
            }
        }
    }
    
    override fun toggleLights() {
        _smartHomeState.update { it.copy(lightsOn = !it.lightsOn) }
    }
    
    override fun toggleThermostat() {
        _smartHomeState.update { it.copy(thermostatOn = !it.thermostatOn) }
    }
    
    override fun setTemperature(temperature: Int) {
        _smartHomeState.update { it.copy(temperature = temperature) }
    }
    
    override fun processLocationUpdate(locationData: LocationData) {
        // Calculate distance to home
        val distance = calculateDistance(
            locationData.latitude, locationData.longitude,
            homeLatitude, homeLongitude
        )
        
        // Update home status based on location
        val isHome = distance <= homeRadius
        updateHomeStatus(isHome)
    }
    
    private fun calculateDistance(
        lat1: Double, lon1: Double,
        lat2: Double, lon2: Double
    ): Double {
        // Simple Euclidean distance for demonstration
        // In a real app, you'd use the Haversine formula
        return Math.sqrt(
            Math.pow(lat1 - lat2, 2.0) + Math.pow(lon1 - lon2, 2.0)
        )
    }
}
