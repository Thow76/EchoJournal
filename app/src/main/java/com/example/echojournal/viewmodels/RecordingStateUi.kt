package com.example.echojournal.viewmodels

data class RecordingUiState(
    val isRecording: Boolean = false,
    val isPaused: Boolean = false,
    val currentFilePath: String? = null,
    val errorMessage: String? = null,
    val showCancelRecordingDialog: Boolean = false
)
