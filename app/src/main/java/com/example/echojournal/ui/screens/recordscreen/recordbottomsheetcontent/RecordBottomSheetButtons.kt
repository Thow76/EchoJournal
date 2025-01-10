package com.example.echojournal.ui.screens.recordscreen.recordbottomsheetcontent

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
import com.example.echojournal.ui.components.CustomGradientButton
import com.example.echojournal.ui.screens.recordscreen.RecordingViewModel
import com.example.echojournal.ui.theme.MaterialColors
import androidx.compose.material.icons.filled.Mic
import androidx.navigation.NavController

//@Composable
//fun RecordBottomSheetButtons(
//    navController: NavController,
//    recordingViewModel: RecordingViewModel,
//    onCloseSheet: () -> Unit
//) {
//    // Collect the isPaused and isRecording states as Compose state
//    val isPaused by recordingViewModel.isPaused.collectAsState()
//    val isRecording by recordingViewModel.isRecording.collectAsState()
//
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp),
//        horizontalArrangement = Arrangement.SpaceEvenly,
//        verticalAlignment = Alignment.Bottom
//    ) {
//        // Left Button: Close (X) icon
//        CustomButton(
//            onClick = {
//                recordingViewModel.stopRecording()
//                onCloseSheet()
//            },
//            modifier = Modifier
//                .size(48.dp), // Smaller circle
//            icon = Icons.Default.Close,
//            shape = CircleShape,
//            iconTint = MaterialTheme.colorScheme.error,
//            backgroundColor = MaterialColors.ErrorContainer95,
//            contentDescription = "Cancel Recording"
//        )
//
//        // Center Button: Check or Microphone icon with RippleEffect
//        Box(
//            contentAlignment = Alignment.Center,
//            modifier = Modifier.size(64.dp) // Larger size to accommodate ripple
//        ) {
//            // The actual Check or Microphone button
//            CustomGradientButton(
//                onClick = {
//                    if (isRecording) {
//                        navController.navigate("createEntry")
//                    }
//                    else if (isPaused) {
//                        // If recording is paused, resume recording
//                        recordingViewModel.resumeRecording()
//                    } else {
//                        // If recording is active, stop recording
//                        recordingViewModel.stopRecording()
//                        onCloseSheet()
//                    }
//                },
//                icon = {
//                    Icon(
//                        imageVector = if (isPaused) Icons.Default.Mic else Icons.Default.Check,
//                        tint = MaterialTheme.colorScheme.onPrimary,
//                        contentDescription = if (isPaused) "Resume Recording" else "Stop Recording"
//                    )
//                },
//                contentDescription = if (isPaused) "Resume Recording" else "Stop Recording"
//            )
//        }
//
//        // Right Button: Pause/Resume icon
//        CustomButton(
//            onClick = {
//                if (isPaused) {
//                    recordingViewModel.resumeRecording()
//                } else {
//                    recordingViewModel.pauseRecording()
//                }
//            },
//            modifier = Modifier
//                .size(48.dp), // Smaller circle
//            icon = if (isPaused) Icons.Default.PlayArrow else Icons.Default.Pause,
//            shape = CircleShape,
//            iconTint = MaterialTheme.colorScheme.primary,
//            backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
//            contentDescription = if (isPaused) "Resume Recording" else "Pause Recording"
//        )
//    }
//}
//

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
            contentDescription = "Cancel Recording"
        )

        // Center Button: Check or Microphone icon
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(64.dp) // Larger size to accommodate ripple
        ) {
            CustomGradientButton(
                onClick = {
                    if (uiState.isRecording && !uiState.isPaused) {
                        navController.navigate("createEntry")
                    } else if (uiState.isPaused) {
                        recordingViewModel.resumeRecording()
                    } else {
                        recordingViewModel.stopRecording()
                        onCloseSheet()
                    }
                },
                icon = {
                    Icon(
                        imageVector = if (uiState.isPaused) Icons.Default.Mic else Icons.Default.Check,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        contentDescription = if (uiState.isPaused) "Resume Recording" else "Stop Recording"
                    )
                },
                contentDescription = if (uiState.isPaused) "Resume Recording" else "Stop Recording"
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
            contentDescription = if (uiState.isPaused) "Resume Recording" else "Pause Recording"
        )
    }
}

