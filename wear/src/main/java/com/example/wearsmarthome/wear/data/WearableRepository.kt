package com.example.wearsmarthome.wear.data

import android.content.Context
import com.example.wearsmarthome.shared.Constants
import com.example.wearsmarthome.shared.data.LocationData
import com.google.android.gms.wearable.CapabilityClient
import com.google.android.gms.wearable.MessageClient
import com.google.android.gms.wearable.NodeClient
import com.google.android.gms.wearable.Wearable
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import com.google.gson.Gson

interface WearableRepository {
    suspend fun sendLocationData(locationData: LocationData)
    suspend fun findMobileNode(): String?
}

class WearableRepositoryImpl @Inject constructor(
    private val context: Context
) : WearableRepository {
    
    private val messageClient: MessageClient = Wearable.getMessageClient(context)
    private val nodeClient: NodeClient = Wearable.getNodeClient(context)
    private val capabilityClient: CapabilityClient = Wearable.getCapabilityClient(context)
    private val gson = Gson()

    override suspend fun sendLocationData(locationData: LocationData) {
        val nodeId = findMobileNode() ?: return
        val locationJson = gson.toJson(locationData)
        messageClient.sendMessage(
            nodeId,
            Constants.PATH_LOCATION_DATA,
            locationJson.toByteArray()
        ).await()
    }

    override suspend fun findMobileNode(): String? {
        val capabilityInfo = capabilityClient
            .getCapability(Constants.MOBILE_CAPABILITY, CapabilityClient.FILTER_REACHABLE)
            .await()
        
        return capabilityInfo.nodes.firstOrNull()?.id ?: run {
            // Fallback to connected nodes if capability not found
            val nodes = nodeClient.connectedNodes.await()
            nodes.firstOrNull { it.isNearby }?.id
        }
    }
}
