package com.example.echojournal.ui.screens.recordscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.echojournal.audioplayback.AudioPlayer
import com.example.echojournal.audiorecorder.AudioRecorder
import com.example.echojournal.repository.AudioRepository
import java.io.File
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import android.util.Log

@HiltViewModel
class RecordingViewModel @Inject constructor(
    private val recorder: AudioRecorder,
    private val player: AudioPlayer,
    private val audioRepository: AudioRepository
) : ViewModel() {

    private val _isRecording = MutableStateFlow(false)
    val isRecording: StateFlow<Boolean> = _isRecording

    private val _isPaused = MutableStateFlow(false)
    val isPaused: StateFlow<Boolean> = _isPaused

    private var currentOutputFile: File? = null

    /**
     * Starts recording by creating a new file and initiating the recorder.
     */
    fun startRecording() {
        viewModelScope.launch {
            try {
                val file = audioRepository.createRecordingFile()
                currentOutputFile = file
                recorder.start(file)
                _isRecording.value = true
                _isPaused.value = false
                Log.d("RecordingViewModel", "Recording started: ${file.absolutePath}")
            } catch (e: Exception) {
                Log.e("RecordingViewModel", "Error starting recording: ${e.localizedMessage}")
            }
        }
    }

    /**
     * Pauses the ongoing recording.
     */
    fun pauseRecording() {
        viewModelScope.launch {
            if (_isRecording.value && !_isPaused.value) {
                try {
                    recorder.pause()
                    _isPaused.value = true
                    Log.d("RecordingViewModel", "Recording paused.")
                } catch (e: Exception) {
                    Log.e("RecordingViewModel", "Error pausing recording: ${e.localizedMessage}")
                }
            }
        }
    }

    /**
     * Resumes a paused recording.
     */
    fun resumeRecording() {
        viewModelScope.launch {
            if (_isRecording.value && _isPaused.value) {
                try {
                    recorder.resume()
                    _isPaused.value = false
                    Log.d("RecordingViewModel", "Recording resumed.")
                } catch (e: Exception) {
                    Log.e("RecordingViewModel", "Error resuming recording: ${e.localizedMessage}")
                }
            }
        }
    }

    /**
     * Stops the recording and returns the file path if successful.
     * @return The absolute path of the recorded file or null if failed.
     */
    fun stopRecording(): String? {
        return try {
            recorder.stop()
            _isRecording.value = false
            _isPaused.value = false
            Log.d("RecordingViewModel", "Recording stopped.")
            currentOutputFile?.absolutePath
        } catch (e: Exception) {
            Log.e("RecordingViewModel", "Error stopping recording: ${e.localizedMessage}")
            null
        }
    }

    /**
     * Cancels the recording and deletes the partial file.
     */
    fun cancelRecording() {
        viewModelScope.launch {
            try {
                recorder.cancel()
                _isRecording.value = false
                _isPaused.value = false
                Log.d("RecordingViewModel", "Recording canceled.")
                currentOutputFile = null
            } catch (e: Exception) {
                Log.e("RecordingViewModel", "Error canceling recording: ${e.localizedMessage}")
            }
        }
    }

    /**
     * Plays the specified audio file.
     * @param file The File to play.
     */
    fun playRecording(file: File) {
        try {
            player.playFile(file)
            Log.d("RecordingViewModel", "Playing recording: ${file.absolutePath}")
        } catch (e: Exception) {
            Log.e("RecordingViewModel", "Error playing recording: ${e.localizedMessage}")
        }
    }

    /**
     * Stops any ongoing playback.
     */
    fun stopPlayback() {
        try {
            player.stop()
            Log.d("RecordingViewModel", "Playback stopped.")
        } catch (e: Exception) {
            Log.e("RecordingViewModel", "Error stopping playback: ${e.localizedMessage}")
        }
    }

    /**
     * Retrieves all recorded audio files.
     * @return List of audio Files.
     */
    fun getAllRecordings(): List<File> {
        return audioRepository.getAllAudioFiles()
    }
}
