package com.example.echojournal.domain.model

data class PlaybackUiState(
    val isFileLoaded: Boolean = false,
    val isPlaybackActive: Boolean = false,
    val currentPosition: Long = 0L,
    val duration: Long? = null,
    val errorMessage: String? = null
)
