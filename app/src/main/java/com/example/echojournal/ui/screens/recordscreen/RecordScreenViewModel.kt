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
import kotlinx.coroutines.flow.update

@HiltViewModel
class RecordingViewModel @Inject constructor(
    private val recorder: AudioRecorder,
    private val audioRepository: AudioRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RecordingUiState())
    val uiState: StateFlow<RecordingUiState> = _uiState

    private var currentOutputFile: File? = null

    fun startRecording() {
        viewModelScope.launch {
            try {
                val file = audioRepository.createRecordingFile()
                currentOutputFile = file
                recorder.start(file)
                _uiState.update {
                    it.copy(
                        isRecording = true,
                        isPaused = false,
                        currentFilePath = file.absolutePath
                    )
                }
                Log.d("RecordingViewModel", "Recording started: ${file.absolutePath}")
            } catch (e: Exception) {
                handleError("Error starting recording: ${e.localizedMessage}")
            }
        }
    }

    fun pauseRecording() {
        viewModelScope.launch {
            if (uiState.value.isRecording && !uiState.value.isPaused) {
                try {
                    recorder.pause()
                    _uiState.update { it.copy(isPaused = true) }
                    Log.d("RecordingViewModel", "Recording paused.")
                } catch (e: Exception) {
                    handleError("Error pausing recording: ${e.localizedMessage}")
                }
            }
        }
    }

    fun resumeRecording() {
        viewModelScope.launch {
            if (uiState.value.isRecording && uiState.value.isPaused) {
                try {
                    recorder.resume()
                    _uiState.update { it.copy(isPaused = false) }
                    Log.d("RecordingViewModel", "Recording resumed.")
                } catch (e: Exception) {
                    handleError("Error resuming recording: ${e.localizedMessage}")
                }
            }
        }
    }

    fun stopRecording(): String? {
        return try {
            recorder.stop()
            _uiState.update { it.copy(isRecording = false, isPaused = false) }
            Log.d("RecordingViewModel", "Recording stopped at: ${currentOutputFile?.absolutePath}")
            currentOutputFile?.absolutePath
        } catch (e: Exception) {
            handleError("Error stopping recording: ${e.localizedMessage}")
            null
        }
    }

    fun onCancelRequest() {
        // When user attempts to navigate away, request showing the dialog
        _uiState.update { currentState ->
            currentState.copy(showCancelRecordingDialog = true)
        }
    }

    fun onConfirmLeave() {
        // Dialog “Leave” clicked, hide the dialog and proceed with leaving
        _uiState.update { currentState ->
            currentState.copy(showCancelRecordingDialog = false)
        }
        cancelRecording()
        // You might also perform navigation or other logic here
    }

    fun onDismissLeave() {
        // Dialog “Cancel” clicked, hide the dialog
        _uiState.update { currentState ->
            currentState.copy(showCancelRecordingDialog = false)

        }
    }

    fun cancelRecording() {
        viewModelScope.launch {
            try {
                recorder.cancel()
                _uiState.update {
                    it.copy(isRecording = false, isPaused = false, currentFilePath = null)
                }
                Log.d("RecordingViewModel", "Recording canceled.")
                currentOutputFile = null
            } catch (e: Exception) {
                handleError("Error canceling recording: ${e.localizedMessage}")
            }
        }
    }

    fun deleteRecording(filePath: String) {
        viewModelScope.launch {
            try {
                val file = File(filePath)
                audioRepository.deleteAudioFile(file)
                _uiState.update {
                    it.copy(
                        currentFilePath = if (filePath == it.currentFilePath) null else it.currentFilePath,
                        errorMessage = null
                    )
                }
                Log.d("RecordingViewModel", "Recording deleted: $filePath")
            } catch (e: Exception) {
                handleError("Error deleting recording: ${e.localizedMessage}")
            }
        }
    }

    private fun handleError(message: String) {
        Log.e("RecordingViewModel", message)
        _uiState.update { it.copy(errorMessage = message) }
    }
}

