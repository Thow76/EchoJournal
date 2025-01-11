package com.example.echojournal.ui.screens.recordscreen

//data class PlaybackUiState(
//    val isPlaybackActive: Boolean = false,
//    val currentFilePath: String? = null,
//    val currentPosition: Long = 0L,
//    val duration: Long? = null,
//    val errorMessage: String? = null
//)

data class PlaybackUiState(
    val isFileLoaded: Boolean = false,
    val isPlaybackActive: Boolean = false,
    val currentPosition: Long = 0L,
    val duration: Long? = null,
    val errorMessage: String? = null
)
