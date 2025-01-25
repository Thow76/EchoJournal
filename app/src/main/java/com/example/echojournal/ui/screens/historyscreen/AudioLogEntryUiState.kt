package com.example.echojournal.ui.screens.historyscreen

data class AudioPlaybackUiState(
    val isFileLoaded: Boolean = false,
    val isPlaybackActive: Boolean = false,
    val currentPosition: Long = 0L,
    val duration: Long? = null,
    val errorMessage: String? = null
)
