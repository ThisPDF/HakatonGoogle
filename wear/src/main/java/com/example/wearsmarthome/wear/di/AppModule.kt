package com.example.wearsmarthome.wear.di

import android.content.Context
import com.example.wearsmarthome.wear.data.LocationRepository
import com.example.wearsmarthome.wear.data.LocationRepositoryImpl
import com.example.wearsmarthome.wear.data.WearableRepository
import com.example.wearsmarthome.wear.data.WearableRepositoryImpl
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.wearable.Wearable
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(
        @ApplicationContext context: Context
    ): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }
    
    @Provides
    @Singleton
    fun provideMessageClient(@ApplicationContext context: Context) = 
        Wearable.getMessageClient(context)
    
    @Provides
    @Singleton
    fun provideNodeClient(@ApplicationContext context: Context) = 
        Wearable.getNodeClient(context)
    
    @Provides
    @Singleton
    fun provideCapabilityClient(@ApplicationContext context: Context) = 
        Wearable.getCapabilityClient(context)
    
    @Provides
    @Singleton
    fun provideLocationRepository(
        fusedLocationProviderClient: FusedLocationProviderClient
    ): LocationRepository {
        return LocationRepositoryImpl(fusedLocationProviderClient)
    }
    
    @Provides
    @Singleton
    fun provideWearableRepository(
        @ApplicationContext context: Context
    ): WearableRepository {
        return WearableRepositoryImpl(context)
    }
}
