package com.example.echojournal.ui.screens.createentryscreen

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import com.example.echojournal.ui.components.audio.AudioPlayerBar
import com.example.echojournal.viewmodels.PlaybackUiState
import com.example.echojournal.ui.components.utils.getMoodColors

@Composable
fun AudioPlayer(
    playbackUiState: PlaybackUiState,
    onPlayPauseClicked: () -> Unit,
    onSeek: (Float) -> Unit,
    mood: String?
) {
    if (playbackUiState.isFileLoaded) {
        Log.d("CreateEntryScreen", "Displaying AudioPlayerBar with duration: ${playbackUiState.duration}")
        val (sliderColor, playbarColor, iconColor) = getMoodColors(mood)
        playbackUiState.duration?.let { duration ->
            key(duration) {
                AudioPlayerBar(
                    isPlaying = playbackUiState.isPlaybackActive,
                    currentPosition = playbackUiState.currentPosition,
                    duration = duration,
                    onPlayPauseClicked = onPlayPauseClicked,
                    onSeek = onSeek,
                    playbarColor = playbarColor,
                    sliderColor = sliderColor,
                    iconColor = iconColor
                )
            }
        }
    }
}
