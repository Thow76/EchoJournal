package com.example.echojournal.ui.screens.recordscreen.recordbottomsheetcontent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.echojournal.ui.components.CustomButton
import com.example.echojournal.ui.components.CustomGradientButton
import com.example.echojournal.ui.screens.recordscreen.RecordingViewModel
import com.example.echojournal.ui.theme.MaterialColors

@Composable
fun RecordBottomSheetButtons(
    recordingViewModel: RecordingViewModel,
    onCloseSheet: () -> Unit
) {
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
        CustomGradientButton(
            onClick = {
                // Cancel or stop logic. For example:
                recordingViewModel.stopRecording()
                //onCloseSheet()
            },
            icon = {
                Icon(
                    Icons.Default.Check,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    contentDescription = null
                )
            },
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