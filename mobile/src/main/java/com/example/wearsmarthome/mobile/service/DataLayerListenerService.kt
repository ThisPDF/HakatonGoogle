package com.example.wearsmarthome.mobile.service

import android.content.Intent
import com.example.wearsmarthome.mobile.data.SmartHomeRepository
import com.example.wearsmarthome.mobile.data.WearableRepository
import com.example.wearsmarthome.shared.Constants
import com.example.wearsmarthome.shared.data.LocationData
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.WearableListenerService
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DataLayerListenerService : WearableListenerService() {

    @Inject
    lateinit var wearableRepository: WearableRepository
    
    @Inject
    lateinit var smartHomeRepository: SmartHomeRepository
    
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val gson = Gson()

    override fun onMessageReceived(messageEvent: MessageEvent) {
        super.onMessageReceived(messageEvent)
        
        when (messageEvent.path) {
            Constants.PATH_LOCATION_DATA -> {
                val locationJson = String(messageEvent.data)
                try {
                    val locationData = gson.fromJson(locationJson, LocationData::class.java)
                    serviceScope.launch {
                        wearableRepository.updateLocationData(locationData)
                        smartHomeRepository.processLocationUpdate(locationData)
                    }
                } catch (e: Exception) {
                    // Log error
                }
            }
        }
    }
}
