package com.example.echojournal.ui.components.audio

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
/**
 * Composable that displays a recording timer.
 *
 * @param isRecording Indicates whether a recording session is active.
 * @param isPaused Indicates whether the recording is currently paused.
 */
@Composable
fun RecordingTimer(
    isRecording: Boolean,
    isPaused: Boolean
) {
    var time by remember { mutableStateOf(0L) }

    // LaunchedEffect that reacts to changes in isRecording and isPaused.
    LaunchedEffect(isRecording, isPaused) {
        if (isRecording && !isPaused) {
            // Start counting when recording starts or resumes.
            while (isRecording && !isPaused) {
                delay(1000L) // Wait for 1 second.
                time += 1000L // Increment time by 1 second.
            }
        } else if (!isRecording) {
            // Reset the timer when recording stops.
            time = 0L
        }
        // If paused, retain the current time without incrementing.
    }

    val formattedTime = formatTime(time)

    Text(
        text = formattedTime,
        style = MaterialTheme.typography.bodySmall,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(top = 8.dp)
    )
}

/**
 * Formats milliseconds into a MM:SS string.
 *
 * @param milliseconds The time in milliseconds.
 * @return A formatted string representing minutes and seconds.
 */
fun formatTime(milliseconds: Long): String {
    val totalSeconds = milliseconds / 1000
    val minutes = (totalSeconds / 60) % 60
    val seconds = totalSeconds % 60
    return String.format("%02d:%02d", minutes, seconds)
}

