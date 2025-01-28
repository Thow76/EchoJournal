package com.example.echojournal.ui.components.audio

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

@Composable
fun AudioPlayerBar(
    isPlaying: Boolean,
    currentPosition: Long,
    duration: Long,
    onPlayPauseClicked: () -> Unit,
    onSeek: (Float) -> Unit,
    playbarShape: Shape = RoundedCornerShape(50.dp), // Default shape
    iconColor: Color = MaterialTheme.colorScheme.primary, // Default color
    playbarColor: Color = MaterialTheme.colorScheme.inverseOnSurface, // Default color
    sliderColor: Color = MaterialTheme.colorScheme.inversePrimary// Default color
) {
    Column(
        modifier = Modifier
            .fillMaxWidth().padding(start = 16.dp, end = 16.dp)
            .background(playbarColor, playbarShape) // Apply customizable color and shape

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(end = 16.dp)
        ) {
            IconButton(
                onClick = onPlayPauseClicked,
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color.White,
                    contentColor = iconColor
                )
            ) {
                Icon(
                    imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = if (isPlaying) "Pause" else "Play",
                    )
            }
            Slider(
                    value = if (duration > 0) currentPosition.toFloat() / duration else 0f,
            onValueChange = { progress -> onSeek(progress) },
            modifier = Modifier.weight(1f),
            colors = SliderDefaults.colors(
                thumbColor = sliderColor, // Customize the thumb color
                activeTrackColor = sliderColor,// Customize the active track color
                inactiveTrackColor = sliderColor // Customize the inactive track color
            ))
            Text(
                text = formatTime(currentPosition),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 8.dp)
            )
            Text(
                text = "/ " + formatTime(duration),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}
