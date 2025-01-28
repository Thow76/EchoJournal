package com.example.echojournal.viewmodels

import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.echojournal.domain.model.AudioPlaybackUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.File
import javax.inject.Inject

@HiltViewModel
class AudioLogEntryViewModel @Inject constructor() : ViewModel() {

    // Map to store MediaPlayers for each entry
    private val mediaPlayers = mutableMapOf<Int, MediaPlayer>()

    // Map to store UI state for each entry
    private val _uiStateMap = MutableStateFlow<Map<Int, AudioPlaybackUiState>>(emptyMap())
    val uiStateMap: StateFlow<Map<Int, AudioPlaybackUiState>> = _uiStateMap

    // Load an audio file for a specific entry
    fun loadAudioFile(entryId: Int, filePath: String) {
        if (!verifyFileExists(filePath)) {
            updateState(entryId) { it.copy(isFileLoaded = false, errorMessage = "File not found: $filePath") }
            return
        }
        releaseMediaPlayer(entryId) // Release any existing MediaPlayer for this entry
        val mediaPlayer = MediaPlayer().apply {
            setDataSource(filePath)
            setOnPreparedListener {
                val fileDuration = it.duration.toLong()
                updateState(entryId) { currentState ->
                    currentState.copy(isFileLoaded = true, duration = fileDuration)
                }
                Log.d("PlaybackViewModel", "File loaded: $filePath with duration: $fileDuration ms")
            }
            setOnCompletionListener {
                mediaPlayers[entryId]?.seekTo(0) // Use the instance from the map
                updateState(entryId) { it.copy(isPlaybackActive = false) }
                Log.d("PlaybackViewModel", "Playback completed for entryId: $entryId")
            }
            prepareAsync()
        }
        mediaPlayers[entryId] = mediaPlayer
    }

    fun togglePlayPause(entryId: Int) {
        // Stop playback for any currently active entry
        stopAllPlaybackExcept(entryId)

        // Handle play/pause for the requested entry
        val mediaPlayer = mediaPlayers[entryId] ?: return
        val isPlaying = _uiStateMap.value[entryId]?.isPlaybackActive ?: false
        if (isPlaying) {
            mediaPlayer.pause()
            updateState(entryId) { it.copy(isPlaybackActive = false) }
            Log.d("AudioLogEntryViewModel", "Playback paused for entryId: $entryId")
        } else {
            mediaPlayer.start()
            trackPlaybackProgress(entryId)
            updateState(entryId) { it.copy(isPlaybackActive = true) }
            Log.d("AudioLogEntryViewModel", "Playback started for entryId: $entryId")
        }
    }

    // Seek to a specific position for a specific entry
    fun seekToPosition(entryId: Int, progress: Float) {
        val mediaPlayer = mediaPlayers[entryId] ?: return
        val newPosition = (progress * (_uiStateMap.value[entryId]?.duration ?: 0L)).toInt()
        mediaPlayer.seekTo(newPosition)
        updateState(entryId) { it.copy(currentPosition = newPosition.toLong()) }
        Log.d("PlaybackViewModel", "Seeked to position: $newPosition for entryId: $entryId")
    }

    // Track playback progress for a specific entry
    private fun trackPlaybackProgress(entryId: Int) {
        val mediaPlayer = mediaPlayers[entryId] ?: return
        val handler = Handler(Looper.getMainLooper())
        handler.post(object : Runnable {
            override fun run() {
                updateState(entryId) { currentState ->
                    currentState.copy(currentPosition = mediaPlayer.currentPosition.toLong())
                }
                if (mediaPlayer.isPlaying) {
                    handler.postDelayed(this, 1000L) // Update every second
                }
            }
        })
    }

    // Stop playback for all entries except the specified one
    private fun stopAllPlaybackExcept(excludedEntryId: Int) {
        val activeEntries = _uiStateMap.value.filter { it.value.isPlaybackActive && it.key != excludedEntryId }
        activeEntries.forEach { (entryId, _) ->
            mediaPlayers[entryId]?.let { player ->
                player.pause()
                player.seekTo(0)
            }
            updateState(entryId) { it.copy(isPlaybackActive = false, currentPosition = 0L) }
            Log.d("AudioLogEntryViewModel", "Stopped playback for entryId: $entryId")
        }
    }

    // Release MediaPlayer for a specific entry
    private fun releaseMediaPlayer(entryId: Int) {
        mediaPlayers[entryId]?.release()
        mediaPlayers.remove(entryId)
    }

    // Verify if the file exists
    private fun verifyFileExists(filePath: String): Boolean {
        val file = File(filePath)
        val exists = file.exists()
        Log.d("PlaybackViewModel", "File exists: $exists, path: $filePath")
        return exists
    }

    // Update UI state for a specific entry
    private fun updateState(entryId: Int, update: (AudioPlaybackUiState) -> AudioPlaybackUiState) {
        _uiStateMap.value = _uiStateMap.value.toMutableMap().apply {
            val currentState = this[entryId] ?: AudioPlaybackUiState()
            this[entryId] = update(currentState)
        }
    }

    override fun onCleared() {
        super.onCleared()
        mediaPlayers.values.forEach { it.release() }
        mediaPlayers.clear()
    }
}
