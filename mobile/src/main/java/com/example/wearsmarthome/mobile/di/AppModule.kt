package com.example.wearsmarthome.mobile.di

import android.content.Context
import com.example.wearsmarthome.mobile.data.SmartHomeRepository
import com.example.wearsmarthome.mobile.data.SmartHomeRepositoryImpl
import com.example.wearsmarthome.mobile.data.WearableRepository
import com.example.wearsmarthome.mobile.data.WearableRepositoryImpl
import com.google.android.gms.wearable.CapabilityClient
import com.google.android.gms.wearable.MessageClient
import com.google.android.gms.wearable.NodeClient
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
    fun provideMessageClient(@ApplicationContext context: Context): MessageClient = 
        Wearable.getMessageClient(context)
    
    @Provides
    @Singleton
    fun provideNodeClient(@ApplicationContext context: Context): NodeClient = 
        Wearable.getNodeClient(context)
    
    @Provides
    @Singleton
    fun provideCapabilityClient(@ApplicationContext context: Context): CapabilityClient = 
        Wearable.getCapabilityClient(context)
    
    @Provides
    @Singleton
    fun provideWearableRepository(
        @ApplicationContext context: Context,
        messageClient: MessageClient,
        nodeClient: NodeClient,
        capabilityClient: CapabilityClient
    ): WearableRepository {
        return WearableRepositoryImpl(context, messageClient, nodeClient, capabilityClient)
    }
    
    @Provides
    @Singleton
    fun provideSmartHomeRepository(): SmartHomeRepository {
        return SmartHomeRepositoryImpl()
    }
}
