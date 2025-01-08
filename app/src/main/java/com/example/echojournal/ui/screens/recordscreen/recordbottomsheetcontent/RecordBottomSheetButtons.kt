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
import com.example.echojournal.ui.components.animations.RippleEffect
import com.example.echojournal.ui.screens.recordscreen.RecordingViewModel
import com.example.echojournal.ui.theme.MaterialColors

//@Composable
//fun RecordBottomSheetButtons(
//    recordingViewModel: RecordingViewModel,
//    onCloseSheet: () -> Unit
//) {
//    Row(
//        modifier = Modifier.fillMaxWidth(),
//        horizontalArrangement = Arrangement.SpaceEvenly,
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        // Left Button: X icon
//        CustomButton(
//            onClick = {
//                // Cancel or stop logic. For example:
//                recordingViewModel.stopRecording()
//                onCloseSheet()
//            },
//            modifier = Modifier
//                .size(48.dp), // Smaller circle
//            icon = Icons.Default.Close,
//            shape = CircleShape,
//            iconTint = MaterialTheme.colorScheme.error,
//            backgroundColor = MaterialColors.ErrorContainer95,
//        )
//        // Center Button: Check/Tick icon (largest)
//        CustomGradientButton(
//            onClick = {
//                // Cancel or stop logic. For example:
//                recordingViewModel.stopRecording()
//                //onCloseSheet()
//            },
//            icon = {
//                Icon(
//                    Icons.Default.Check,
//                    tint = MaterialTheme.colorScheme.onPrimary,
//                    contentDescription = null
//                )
//            },
//            contentDescription = ""
//        )
//        // Right Button: Pause icon
//        CustomButton(
//            onClick = {
//                // Cancel or stop logic. For example:
//                recordingViewModel.stopRecording()
//                onCloseSheet()
//            },
//            modifier = Modifier
//                .size(48.dp), // Smaller circle
//            icon = Icons.Default.Pause,
//            shape = CircleShape,
//            iconTint = MaterialTheme.colorScheme.primary,
//            backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
//        )
//    }
//}

//@Composable
//fun RecordBottomSheetButtons(
//    recordingViewModel: RecordingViewModel,
//    onCloseSheet: () -> Unit
//) {
//    // Collect the isPaused state as a Compose state
//    val isPaused by recordingViewModel.isPaused.collectAsState()
//    val isRecording by recordingViewModel.isRecording.collectAsState()
//
//    Row(
//        modifier = Modifier.fillMaxWidth(),
//        horizontalArrangement = Arrangement.SpaceEvenly,
//        verticalAlignment = Alignment.CenterVertically
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
//        )
//
//        // Center Button: Check/Tick icon with RippleEffect
//        Box(
//            contentAlignment = Alignment.Center,
//            modifier = Modifier.size(64.dp) // Larger size to accommodate ripple
//        ) {
//            // Ripple Effect only when recording
//            if (isRecording) {
//                RippleEffect(
//                    modifier = Modifier.size(64.dp),
//                    color = MaterialTheme.colorScheme.primary,
//                    rippleCount = 2,
//                    initialRadius = 24f,
//                    maxRadius = 32f,
//                    animationDuration = 1000
//                )
//            }
//
//            // The actual Check button
//            CustomGradientButton(
//                onClick = {
//                    recordingViewModel.stopRecording()
//                    onCloseSheet()
//                },
//                icon = {
//                    Icon(
//                        Icons.Default.Check,
//                        tint = MaterialTheme.colorScheme.onPrimary,
//                        contentDescription = "Stop Recording"
//                    )
//                },
//                contentDescription = "Stop Recording"
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
//        )
//    }
//}

@Composable
fun RecordBottomSheetButtons(
    recordingViewModel: RecordingViewModel,
    onCloseSheet: () -> Unit
) {
    // Collect the isPaused and isRecording states as Compose state
    val isPaused by recordingViewModel.isPaused.collectAsState()
    val isRecording by recordingViewModel.isRecording.collectAsState()

    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom
    ) {
        // Left Button: Close (X) icon
        CustomButton(
            onClick = {
                recordingViewModel.stopRecording()
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

        // Center Button: Check/Tick icon with RippleEffect
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(64.dp) // Larger size to accommodate ripple
        ) {
            // Ripple Effect only when recording
            if (isRecording) {
                RippleEffect(
                    modifier = Modifier.size(64.dp),
                    color = MaterialTheme.colorScheme.primary,
                    rippleCount = 2,
                    initialRadius = 24f,
                    maxRadius = 32f,
                    animationDuration = 1000
                )
            }

            // The actual Check button
            CustomGradientButton(
                onClick = {
                    recordingViewModel.stopRecording()
                    onCloseSheet()
                }, // Button size
                icon = {
                    Icon(
                        Icons.Default.Check,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        contentDescription = "Stop Recording"
                    )
                },
                contentDescription = "Stop Recording"
            )
        }

        // Right Button: Pause/Resume icon
        CustomButton(
            onClick = {
                if (isPaused) {
                    recordingViewModel.resumeRecording()
                } else {
                    recordingViewModel.pauseRecording()
                }
            },
            modifier = Modifier
                .size(48.dp), // Smaller circle
            icon = if (isPaused) Icons.Default.PlayArrow else Icons.Default.Pause,
            shape = CircleShape,
            iconTint = MaterialTheme.colorScheme.primary,
            backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
            contentDescription = if (isPaused) "Resume Recording" else "Pause Recording"
        )
    }
}

