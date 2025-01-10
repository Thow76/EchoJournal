package com.example.echojournal.ui.screens.recordscreen

data class PlaybackUiState(
    val isPlaybackActive: Boolean = false,
    val currentFilePath: String? = null,
    val currentPosition: Long = 0L,
    val duration: Long? = null,
    val errorMessage: String? = null
)
