package com.example.echojournal.ui.screens.recordscreen

import java.io.File

data class RecordingUiState(
    val isRecording: Boolean = false,
    val isPaused: Boolean = false,
    val currentFilePath: String? = null,
    val errorMessage: String? = null
)
