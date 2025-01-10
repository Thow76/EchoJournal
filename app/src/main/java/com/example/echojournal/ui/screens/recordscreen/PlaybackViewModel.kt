package com.example.echojournal.ui.screens.recordscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.echojournal.audioplayback.AudioPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class PlaybackViewModel @Inject constructor(
    private val player: AudioPlayer
) : ViewModel() {

    private val _uiState = MutableStateFlow(PlaybackUiState())
    val uiState: StateFlow<PlaybackUiState> = _uiState

    private fun playRecording(file: File) {
        try {
            player.playFile(file)
            _uiState.update {
                it.copy(
                    isPlaybackActive = true,
                    currentFilePath = file.absolutePath,
                    duration = player.duration()
                )
            }
            updatePlaybackPosition()
        } catch (e: Exception) {
            handleError("Error playing recording: ${e.localizedMessage}")
        }
    }

    fun togglePlayPause() {
        if (_uiState.value.isPlaybackActive) {
            stopPlayback()
        } else {
            _uiState.value.currentFilePath?.let { playRecording(File(it)) }
        }
    }

    private fun stopPlayback() {
        try {
            player.stop()
            _uiState.update { it.copy(isPlaybackActive = false) }
        } catch (e: Exception) {
            handleError("Error stopping playback: ${e.localizedMessage}")
        }
    }

    fun seekToPosition(progress: Float) {
        val newPosition = (progress * (_uiState.value.duration ?: 0L)).toLong()
        player.seekTo(newPosition)
        _uiState.update { it.copy(currentPosition = newPosition) }
    }

    private fun updatePlaybackPosition() {
        viewModelScope.launch {
            while (_uiState.value.isPlaybackActive) {
                _uiState.update { it.copy(currentPosition = player.getCurrentPosition()) }
                delay(100)
            }
        }
    }

    private fun handleError(message: String) {
        Log.e("PlaybackViewModel", message)
        _uiState.update { it.copy(errorMessage = message) }
    }
}
