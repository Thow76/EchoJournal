package com.example.echojournal.ui.screens.recordscreen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.echojournal.ui.components.CustomAppBar
import com.example.echojournal.ui.components.ErrorSnackbar
import com.example.echojournal.ui.components.LoadingIndicator
import com.example.echojournal.ui.theme.Gradients
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.echojournal.ui.components.AudioPlayerBar
import com.example.echojournal.ui.screens.historyscreen.JournalHistoryScreenEmpty
import com.example.echojournal.ui.screens.historyscreen.JournalHistoryScreenList
import com.example.echojournal.ui.screens.historyscreen.JournalHistoryViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun CreateEntryScreen(
    navController: NavController,
    journalHistoryViewModel: JournalHistoryViewModel = hiltViewModel(),
    playbackViewModel: PlaybackViewModel = hiltViewModel(),
    audioFilePath: String? = null
) {
    val journalUiState by journalHistoryViewModel.uiState.collectAsState()
    val playbackUiState by playbackViewModel.uiState.collectAsState()

    // Debug logs
    LaunchedEffect(audioFilePath) {
        if (!playbackUiState.isFileLoaded && audioFilePath != null) {
            Log.d("CreateEntryScreen", "Loading file: $audioFilePath")
            playbackViewModel.loadAudioFile(audioFilePath)
        }
    }

    Log.d(
        "CreateEntryScreen",
        "UI State: isFileLoaded=${playbackUiState.isFileLoaded}, duration=${playbackUiState.duration}"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Gradients.BgSaturateGradient)
    ) {
        Scaffold(
            snackbarHost = { SnackbarHost(hostState = remember { SnackbarHostState() }) },
            containerColor = Color.Transparent,
            topBar = { CustomAppBar(title = "New Entry") },
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                // Show Audio Player if a file is loaded
                if (playbackUiState.isFileLoaded) {
                    Log.d(
                        "CreateEntryScreen",
                        "Displaying AudioPlayerBar with duration: ${playbackUiState.duration}"
                    )
                    playbackUiState.duration?.let { duration ->
                        key(duration) {
                            AudioPlayerBar(
                                isPlaying = playbackUiState.isPlaybackActive,
                                currentPosition = playbackUiState.currentPosition,
                                duration = duration,
                                onPlayPauseClicked = { playbackViewModel.togglePlayPause() },
                                onSeek = { playbackViewModel.seekToPosition(it) },
                                trackTitle = "Recorded Audio"
                            )
                        }

                    }
                    // Other UI components...
                }
            }
        }
    }
}

