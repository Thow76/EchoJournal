package com.example.echojournal.ui.screens.recordscreen

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
    playbackViewModel: PlaybackViewModel = hiltViewModel()
) {
    val journalUiState by journalHistoryViewModel.uiState.collectAsState()
    val playbackUiState by playbackViewModel.uiState.collectAsState()

    val showMoodSheet = remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Gradients.BgSaturateGradient)
    ) {
        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            containerColor = Color.Transparent,
            topBar = {
                CustomAppBar(title = "New Entry")
            },
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                playbackUiState.duration?.let {
                    AudioPlayerBar(
                        isPlaying = playbackUiState.isPlaybackActive,
                        currentPosition = playbackUiState.currentPosition,
                        duration = it,
                        onPlayPauseClicked = { playbackViewModel.togglePlayPause() },
                        onSeek = { progress -> playbackViewModel.seekToPosition(progress) }
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                ) {
                    when {
                        journalUiState.isLoading -> LoadingIndicator()
                        journalUiState.errorMessage != null -> ErrorSnackbar(
                            modifier = Modifier.align(Alignment.BottomCenter),
                            errorMessage = "Error: ${journalUiState.errorMessage}",
                            onDismiss = journalHistoryViewModel::clearErrorMessage
                        )
                        journalUiState.journalEntries.isNotEmpty() -> JournalHistoryScreenList(journalEntries = journalUiState.journalEntries)
                        else -> JournalHistoryScreenEmpty(paddingValues)
                    }
                }
            }
        }
    }

    if (showMoodSheet.value) {
        ModalBottomSheet(
            onDismissRequest = {
                showMoodSheet.value = false
            },
            sheetState = sheetState
        ) {
            MoodBottomSheet()
        }
    }
}

