package com.example.echojournal.ui.screens.recordscreen

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.echojournal.ui.components.CustomButton
import com.example.echojournal.ui.components.GradientFloatingActionButton
import com.example.echojournal.ui.theme.Gradients
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
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left Button: X icon
            CustomButton(
                onClick = {
                    // Cancel or stop logic. For example:
                    recordingViewModel.stopRecording()
                    onCloseSheet()
                },
                modifier = Modifier
                    .size(48.dp), // Smaller circle
            icon = Icons.Default.Close,
                shape = CircleShape,
                iconTint = MaterialTheme.colorScheme.error,
                backgroundColor = MaterialColors.ErrorContainer95,
            )
            // Center Button: Check/Tick icon (largest)
            GradientFloatingActionButton(
                onClick = {
                 // Cancel or stop logic. For example:
                    recordingViewModel.stopRecording()
                    //onCloseSheet()
                    },
                icon = {
                    Icon(
                    Icons.Default.Check,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    contentDescription = null)},
                contentDescription = ""
            )
            // Right Button: Pause icon
            CustomButton(
                onClick = {
                    // Cancel or stop logic. For example:
                    recordingViewModel.stopRecording()
                    onCloseSheet()
                },
                modifier = Modifier
                    .size(48.dp), // Smaller circle
                icon = Icons.Default.Pause,
                shape = CircleShape,
                iconTint = MaterialTheme.colorScheme.primary,
                backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
            )
            }



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

