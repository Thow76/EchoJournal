package com.example.echojournal.ui.screens.recordscreen

import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.io.File
import javax.inject.Inject

@HiltViewModel
class PlaybackViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(PlaybackUiState())
    val uiState: StateFlow<PlaybackUiState> = _uiState

    private var mediaPlayer: MediaPlayer? = null

    fun loadAudioFile(filePath: String) {
        if (!verifyFileExists(filePath)) {
            _uiState.update { it.copy(isFileLoaded = false, errorMessage = "File not found: $filePath") }
            return
        }
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer().apply {
            setDataSource(filePath)
            setOnPreparedListener {
                val fileDuration = it.duration.toLong()
                _uiState.update { currentState ->
                    currentState.copy(isFileLoaded = true, duration = fileDuration)
                }
                Log.d("PlaybackViewModel", "File loaded: $filePath with duration: $fileDuration ms")
            }
            setOnCompletionListener {
                _uiState.update { it.copy(isPlaybackActive = false) }
                mediaPlayer?.seekTo(0)
                Log.d("PlaybackViewModel", "Playback completed.")
            }
            prepareAsync()
        }
    }


    fun togglePlayPause() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.pause()
                _uiState.update { it.copy(isPlaybackActive = false) }
                Log.d("PlaybackViewModel", "Playback paused.")
            } else {
                it.start()
                trackPlaybackProgress()
                _uiState.update { it.copy(isPlaybackActive = true) }
                Log.d("PlaybackViewModel", "Playback started.")
            }
        }
    }

    fun seekToPosition(progress: Float) {
        val newPosition = (progress * (uiState.value.duration ?: 0L)).toInt()
        mediaPlayer?.seekTo(newPosition)
        _uiState.update { it.copy(currentPosition = newPosition.toLong()) }
        Log.d("PlaybackViewModel", "Seeked to position: $newPosition")
    }

    private fun trackPlaybackProgress() {
        mediaPlayer?.let { player ->
            val handler = Handler(Looper.getMainLooper())
            handler.post(object : Runnable {
                override fun run() {
                    _uiState.update { currentState ->
                        currentState.copy(currentPosition = player.currentPosition.toLong())
                    }
                    if (player.isPlaying) {
                        handler.postDelayed(this, 1000L) // Update every second
                    }
                }
            })
        }
    }

    private fun verifyFileExists(filePath: String): Boolean {
        val file = File(filePath)
        val exists = file.exists()
        Log.d("PlaybackViewModel", "File exists: $exists, path: $filePath")
        return exists
    }



    override fun onCleared() {
        super.onCleared()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}

