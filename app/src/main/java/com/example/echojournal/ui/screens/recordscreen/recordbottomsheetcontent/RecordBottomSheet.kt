package com.example.echojournal.ui.screens.recordscreen.recordbottomsheetcontent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.echojournal.ui.components.CustomButton
import com.example.echojournal.ui.components.CustomGradientButton
import com.example.echojournal.ui.screens.recordscreen.RecordingViewModel
import com.example.echojournal.ui.theme.MaterialColors


@Composable
fun RecordSheetContent(
    recordingViewModel: RecordingViewModel,
    onCloseSheet: () -> Unit,
    onComplete: (String) -> Unit
) {
    // Example path; replace with real path logic as needed
    val dummyFilePath = "/some/fake/path/audio_${System.currentTimeMillis()}.mp4"

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 1. Title / Prompt
        Text(
            text = "Recording your memories...",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 2. Row of three circular buttons
        RecordBottomSheetButtons(recordingViewModel, onCloseSheet)
        }
        Spacer(modifier = Modifier.height(24.dp))
        // 3. (Optional) Show current recording state or any other UI
        Text(
            text = when {
                recordingViewModel.isPaused -> "Recording paused..."
                recordingViewModel.isRecording -> "Recording in progress..."
                else -> ""
            },
            style = MaterialTheme.typography.bodyLarge
        )
    }



