package com.example.echojournal.ui.screens.historyscreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.echojournal.R
import com.example.echojournal.ui.components.CustomAppBar
import com.example.echojournal.ui.components.ErrorSnackbar
import com.example.echojournal.ui.components.CustomGradientButton
import com.example.echojournal.ui.components.LoadingIndicator
import com.example.echojournal.ui.screens.recordscreen.recordbottomsheetcontent.RecordSheetContent
import com.example.echojournal.ui.screens.recordscreen.RecordingViewModel
import com.example.echojournal.ui.theme.Gradients
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import com.example.echojournal.ui.components.*
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun JournalHistoryScreen(
    navController: NavController,
    journalHistoryViewModel: JournalHistoryViewModel = hiltViewModel(),
    recordingViewModel: RecordingViewModel = hiltViewModel()
) {
    val uiState by journalHistoryViewModel.uiState.collectAsState()

    // Permission State
    val recordAudioPermissionState = rememberPermissionState(android.Manifest.permission.RECORD_AUDIO)

    // Control for showing/hiding the bottom sheet
    val showRecordSheet = remember { mutableStateOf(false) }

    // State to track if the user has requested recording
    val isRecordingRequested = remember { mutableStateOf(false) }

    // Remember a sheet state (to control partial vs full expansion)
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    // Snackbar support
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // LaunchedEffect to handle permission grant when recording is requested
    LaunchedEffect(recordAudioPermissionState.status) {
        if (recordAudioPermissionState.status == PermissionStatus.Granted && isRecordingRequested.value) {
            Log.d("JournalHistoryScreen", "Permission granted. Starting recording.")
            isRecordingRequested.value = false // Reset the request flag
            showRecordSheet.value = true
            recordingViewModel.startRecording()
        } else if (recordAudioPermissionState.status is PermissionStatus.Denied && isRecordingRequested.value) {
            Log.d("JournalHistoryScreen", "Permission denied.")
            isRecordingRequested.value = false // Reset the request flag
            coroutineScope.launch {
                snackbarHostState.showSnackbar("Permission denied. Cannot record audio.")
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Gradients.BgSaturateGradient)
    ) {
        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            containerColor = Color.Transparent,
            topBar = {
                CustomAppBar(title = stringResource(id = R.string.history_screen_heading))
            },
            floatingActionButton = {
                CustomGradientButton(
                    onClick = {
                        coroutineScope.launch {
                            if (recordAudioPermissionState.status != PermissionStatus.Granted) {
                                // User has initiated a recording request
                                Log.d("JournalHistoryScreen", "Requesting recording permission.")
                                isRecordingRequested.value = true
                                recordAudioPermissionState.launchPermissionRequest()
                            } else {
                                // Permission already granted; start recording
                                Log.d("JournalHistoryScreen", "Permission already granted. Starting recording.")
                                showRecordSheet.value = true
                                recordingViewModel.startRecording()
                            }
                        }
                    },
                    icon = {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            imageVector = Icons.Default.Add,
                            contentDescription = "Record",
                            tint = Color.White
                        )
                    },
                    contentDescription = "Record a new entry"
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                FilterSection(
                    viewModel = journalHistoryViewModel
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                ) {
                    when {
                        uiState.isLoading -> {
                            LoadingIndicator()
                        }

                        uiState.errorMessage != null -> {
                            ErrorSnackbar(
                                modifier = Modifier.align(Alignment.BottomCenter),
                                errorMessage = "Error: ${uiState.errorMessage}",
                                onDismiss = journalHistoryViewModel::clearErrorMessage
                            )
                        }

                        uiState.journalEntries.isNotEmpty() -> {
                            JournalHistoryScreenList(journalEntries = uiState.journalEntries)
                        }

                        else -> {
                            JournalHistoryScreenEmpty(paddingValues)
                        }
                    }
                }
            }
        }
    }

    // If showRecordSheet is true, display the modal bottom sheet
    if (showRecordSheet.value) {
        ModalBottomSheet(
            onDismissRequest = {
                // If user swipes down or taps outside, hide the sheet and stop recording
                Log.d("JournalHistoryScreen", "Bottom sheet dismissed. Stopping recording.")
                showRecordSheet.value = false
                recordingViewModel.stopRecording()
            },
            sheetState = sheetState
        ) {
            // Our actual bottom sheet UI for recording
            RecordSheetContent(
                recordingViewModel = recordingViewModel,
                onCloseSheet = {
                    Log.d("JournalHistoryScreen", "Close sheet clicked. Stopping recording.")
                    showRecordSheet.value = false
                    recordingViewModel.stopRecording()
                },
                onComplete = { filePath ->
                    // Handle the completed recording
                    Log.d("JournalHistoryScreen", "Recording completed. Saved at $filePath")
                    showRecordSheet.value = false
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Recording saved: $filePath")
                    }
                    // Optionally, refresh journal entries or navigate
                    journalHistoryViewModel.loadJournalEntries()
                }
            )
        }
    }
}








