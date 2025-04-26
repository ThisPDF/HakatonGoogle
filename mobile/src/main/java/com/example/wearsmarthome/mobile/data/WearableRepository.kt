package com.example.wearsmarthome.mobile.data

import android.content.Context
import com.example.wearsmarthome.shared.Constants
import com.example.wearsmarthome.shared.data.LocationData
import com.google.android.gms.wearable.CapabilityClient
import com.google.android.gms.wearable.MessageClient
import com.google.android.gms.wearable.NodeClient
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

interface WearableRepository {
    val locationData: StateFlow<LocationData?>
    fun updateLocationData(locationData: LocationData)
}

class WearableRepositoryImpl @Inject constructor(
    private val context: Context,
    private val messageClient: MessageClient,
    private val nodeClient: NodeClient,
    private val capabilityClient: CapabilityClient
) : WearableRepository {

    private val _locationData = MutableStateFlow<LocationData?>(null)
    override val locationData: StateFlow<LocationData?> = _locationData.asStateFlow()
    
    private val gson = Gson()

    override fun updateLocationData(locationData: LocationData) {
        _locationData.update { locationData }
    }
}
