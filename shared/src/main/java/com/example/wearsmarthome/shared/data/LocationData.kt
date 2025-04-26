package com.example.wearsmarthome.shared.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.Instant

@Parcelize
data class LocationData(
    val latitude: Double,
    val longitude: Double,
    val accuracy: Float,
    val timestamp: Long = Instant.now().toEpochMilli()
) : Parcelable
