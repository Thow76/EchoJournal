package com.example.echojournal.ui.screens.recordscreen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.echojournal.ui.components.CustomAppBar
import com.example.echojournal.ui.components.ErrorSnackbar
import com.example.echojournal.ui.components.LoadingIndicator
import com.example.echojournal.ui.theme.Gradients
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.echojournal.ui.components.AudioPlayerBar
import com.example.echojournal.ui.components.CustomButton
import com.example.echojournal.ui.components.CustomGradientButton
import com.example.echojournal.ui.screens.historyscreen.JournalHistoryScreenEmpty
import com.example.echojournal.ui.screens.historyscreen.JournalHistoryScreenList
import com.example.echojournal.ui.screens.historyscreen.JournalHistoryViewModel
import com.example.echojournal.ui.theme.MaterialColors
import com.example.echojournal.ui.theme.Palettes
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
            containerColor = Color.White,
            topBar = {
                CustomAppBar(
                    title = "New Entry",
                    onNavigationClick = {}
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

                       TextField(
                           value = "", // Current text value
                           onValueChange = {}, // Lambda to handle text changes
                           modifier = Modifier
                               .fillMaxWidth()
                               .background(Color.Transparent), // Transparent background
                           textStyle = MaterialTheme.typography.headlineLarge.copy(
                               textAlign = TextAlign.Start // Align text to the start
                           ),
                           label = {
                               Text(
                                   text = "Add Title...",
                                   style = MaterialTheme.typography.headlineLarge,
                                   color = MaterialColors.OutlineVariantNeutralVariant80)
                                   }, // Label text
                           leadingIcon = null, // Set leadingIcon to null if not needed
                           colors = TextFieldDefaults.textFieldColors(
                               containerColor = Color.Transparent, // Transparent text field background
                               cursorColor = MaterialTheme.colorScheme.primary, // Cursor color
                               focusedIndicatorColor = Color.Transparent, // Transparent underline when focused
                               unfocusedIndicatorColor = Color.Transparent // Transparent underline when unfocused
                           )
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
                    // Other UI components...
                }
            }
        }
    }
}

