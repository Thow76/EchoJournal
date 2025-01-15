package com.example.echojournal.ui.createentryscreen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.echojournal.ui.components.CustomAppBar
import com.example.echojournal.ui.theme.Gradients
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.echojournal.ui.components.AudioPlayerBar
import com.example.echojournal.ui.components.CustomGradientButton
import com.example.echojournal.ui.components.CustomTextField
import com.example.echojournal.ui.screens.historyscreen.JournalHistoryViewModel
import com.example.echojournal.ui.screens.recordscreen.PlaybackViewModel
import com.example.echojournal.ui.components.TopicSearchAndCreate
import com.example.echojournal.ui.theme.MaterialColors
import com.example.echojournal.ui.theme.Palettes
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun CreateEntryScreen(
    navController: NavController,
    journalHistoryViewModel: JournalHistoryViewModel = hiltViewModel(),
    playbackViewModel: PlaybackViewModel = hiltViewModel(),
    topicViewModel: TopicViewModel = hiltViewModel(),
    audioFilePath: String? = null
) {
    val journalUiState by journalHistoryViewModel.uiState.collectAsState()
    val playbackUiState by playbackViewModel.uiState.collectAsState()

    // State to hold the text field value
    var addTitleTextFieldValue by remember { mutableStateOf("") }
    var addTopicTextFieldValue by remember { mutableStateOf("") }

    // For simplicity, here’s a hardcoded mutable list:
    val topics = remember { mutableStateListOf("Android", "Compose", "Kotlin") }

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
            containerColor = Color.White,
            topBar = {
                CustomAppBar(
                    title = "New Entry",
                    onNavigationClick = {navController.navigateUp() }
                ) },
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
               Row(modifier = Modifier
                   .fillMaxWidth()
                   .padding(start = 16.dp, end = 8.dp),
                    horizontalArrangement = Arrangement.Start,
                   verticalAlignment = Alignment.CenterVertically
                   ) {
                   CustomGradientButton(
                       modifier = Modifier.size(32.dp),
                       onClick = {},
                       icon = {
                           Icon(modifier = Modifier.size(24.dp),
                               imageVector = Icons.Default.Add,
                               contentDescription = "Record",
                               tint = Palettes.Secondary70) },
                        contentDescription = "Record a new entry",
                       buttonGradient = Gradients.BgSaturateGradient

                   )
                   CustomTextField(
                       value = addTitleTextFieldValue,
                       onValueChange = { newValue -> addTitleTextFieldValue = newValue },
                       placeholderText = "Add Title...",
                       modifier = Modifier.padding(0.dp),
                       textStyle = MaterialTheme.typography.headlineLarge.copy(
                           textAlign = TextAlign.Start
                       ),
                       leadingIcon = null,
                       placeholderColor = MaterialColors.OutlineVariantNeutralVariant80,
                       containerColor = Color.Transparent
                   )
                }
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
                            )
                        }

                    }

                }
                TopicSearchAndCreate (viewModel = topicViewModel)
            }
        }
    }
}

