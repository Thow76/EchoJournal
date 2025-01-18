package com.example.echojournal.ui.screens.recordscreen.recordbottomsheetcontent

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.echojournal.ui.components.CustomButton
import com.example.echojournal.ui.components.CustomGradientIconButton
import com.example.echojournal.ui.screens.recordscreen.RecordingViewModel
import com.example.echojournal.ui.theme.MaterialColors
import androidx.compose.material.icons.filled.Mic
import androidx.navigation.NavController

@Composable
fun RecordBottomSheetButtons(
    navController: NavController,
    recordingViewModel: RecordingViewModel,
    onCloseSheet: () -> Unit
) {
    val uiState by recordingViewModel.uiState.collectAsState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom
    ) {
        // Left Button: Close (X) icon
        CustomButton(
            onClick = {
                recordingViewModel.cancelRecording()
                onCloseSheet()
            },
            modifier = Modifier
                .size(48.dp), // Smaller circle
            icon = Icons.Default.Close,
            shape = CircleShape,
            iconTint = MaterialTheme.colorScheme.error,
            backgroundColor = MaterialColors.ErrorContainer95,
            contentDescription = "Cancel Recording",
            enabled = null
        )

        // Center Button: Check or Microphone icon
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(64.dp) // Larger size to accommodate ripple
        ) {
            CustomGradientIconButton(
                onClick = {
                    when {
                        uiState.isRecording && !uiState.isPaused -> {
                            // Stop recording and navigate to the next screen
                            val recordedFilePath: String? = recordingViewModel.stopRecording()
                            if (recordedFilePath != null) {
                                Log.d("RecordBottomSheetButtons", "Navigating with file path: $recordedFilePath")
                                navController.navigate("createEntry?filePath=${Uri.encode(recordedFilePath)}")
                                onCloseSheet()
                            } else {
                                Log.e("RecordBottomSheetButtons", "Failed to retrieve recorded file path.")
                            }
                        }
                        uiState.isPaused -> {
                            // Resume recording
                            recordingViewModel.resumeRecording()
                        }
                        else -> {
                            // Start a new recording
                            recordingViewModel.startRecording()
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = when {
                            uiState.isPaused -> Icons.Default.Mic
                            uiState.isRecording -> Icons.Default.Check
                            else -> Icons.Default.Mic
                        },
                        tint = MaterialTheme.colorScheme.onPrimary,
                        contentDescription = when {
                            uiState.isPaused -> "Resume Recording"
                            uiState.isRecording -> "Stop Recording"
                            else -> "Start Recording"
                        }
                    )
                },
                contentDescription = when {
                    uiState.isPaused -> "Resume Recording"
                    uiState.isRecording -> "Stop Recording"
                    else -> "Start Recording"
                }
            )

        }

        // Right Button: Pause/Resume icon
        CustomButton(
            onClick = {
                if (uiState.isPaused) {
                    recordingViewModel.resumeRecording()
                } else {
                    recordingViewModel.pauseRecording()
                }
            },
            modifier = Modifier
                .size(48.dp), // Smaller circle
            icon = if (uiState.isPaused) Icons.Default.PlayArrow else Icons.Default.Pause,
            shape = CircleShape,
            iconTint = MaterialTheme.colorScheme.primary,
            backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
            contentDescription = if (uiState.isPaused) "Resume Recording" else "Pause Recording",
            enabled = null
        )
    }
}

