package com.example.wearsmarthome.wear.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Text

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Smart Home",
            textAlign = TextAlign.Center
        )
        
        Text(
            text = if (uiState.isTracking) "Location tracking active" else "Location tracking inactive",
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        
        Button(
            onClick = { 
                if (uiState.isTracking) {
                    viewModel.stopTracking()
                } else {
                    viewModel.startTracking()
                }
            }
        ) {
            Text(text = if (uiState.isTracking) "Stop" else "Start")
        }
        
        if (uiState.lastLocation != null) {
            Text(
                text = "Last location: ${String.format("%.4f, %.4f", 
                    uiState.lastLocation?.latitude, 
                    uiState.lastLocation?.longitude)}",
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}
