package com.example.echojournal.ui.screens.recordscreen.recordbottomsheetcontent

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
import androidx.compose.ui.unit.dp
import com.example.echojournal.ui.components.RecordingTimer
import com.example.echojournal.ui.screens.recordscreen.RecordingViewModel

@Composable
fun RecordSheetContent(
    recordingViewModel: RecordingViewModel,
    onCloseSheet: () -> Unit,
    onComplete: (String) -> Unit
) {
    // Collect the recording states once to avoid multiple collectAsState() calls
    val isRecording by recordingViewModel.isRecording.collectAsState()
    val isPaused by recordingViewModel.isPaused.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp), // Optional: Add padding as needed
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Conditionally display messages based on the recording state
        if (isPaused) {
            Text(
                text = "Recording is paused",
                style = MaterialTheme.typography.titleLarge,
            )
        } else if (isRecording) {
            Text(
                text = "Recording your memories...",
                style = MaterialTheme.typography.titleLarge,
            )
        }

        // Display the Recording Timer
        RecordingTimer(
            isRecording = isRecording,
            isPaused = isPaused
        )

        Spacer(modifier = Modifier.height(32.dp)) // Space between timer and buttons

        // Recording Buttons with Ripple Effect
        RecordBottomSheetButtons(
            recordingViewModel = recordingViewModel,
            onCloseSheet = onCloseSheet
        )
    }
}



