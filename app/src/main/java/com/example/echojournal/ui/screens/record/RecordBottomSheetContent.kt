package com.example.echojournal.ui.screens.record

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.echojournal.R
import com.example.echojournal.ui.components.audio.RecordingTimer
import com.example.echojournal.viewmodels.RecordingViewModel

@Composable
fun RecordBottomSheetContent(
    navController: NavController,
    recordingViewModel: RecordingViewModel,
    onCloseSheet: () -> Unit,
    onComplete: (String) -> Unit
) {
    val uiState by recordingViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp), // Optional: Add padding as needed
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Conditionally display messages based on the recording state
        when {
            uiState.isPaused -> {
                Text(
                    text = stringResource(R.string.record_bottom_sheet_recording_is_paused),
                    style = MaterialTheme.typography.titleLarge,
                )
            }
            uiState.isRecording -> {
                Text(
                    text = stringResource(R.string.record_bottom_sheet_recording_your_memories),
                    style = MaterialTheme.typography.titleLarge,
                )
            }
        }

        // Display the Recording Timer
        RecordingTimer(
            isRecording = uiState.isRecording,
            isPaused = uiState.isPaused
        )

        Spacer(modifier = Modifier.height(32.dp)) // Space between timer and buttons

        // Recording Buttons with Ripple Effect
        RecordBottomSheetButtons(
            recordingViewModel = recordingViewModel,
            onCloseSheet = onCloseSheet,
            navController = navController
        )
    }
}




