package com.example.echojournal.ui.screens.createentryscreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.echojournal.model.JournalEntry
import com.example.echojournal.ui.components.CustomAlertDialog
import com.example.echojournal.ui.components.CustomAppBar
import com.example.echojournal.ui.screens.historyscreen.JournalHistoryViewModel
import com.example.echojournal.ui.screens.recordscreen.PlaybackViewModel
import com.example.echojournal.ui.screens.recordscreen.RecordingViewModel
import com.example.echojournal.ui.theme.Gradients
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun CreateEntryScreen(
    navController: NavController,
    journalHistoryViewModel: JournalHistoryViewModel = hiltViewModel(),
    playbackViewModel: PlaybackViewModel = hiltViewModel(),
    topicViewModel: TopicViewModel = hiltViewModel(),
    recordingViewModel: RecordingViewModel = hiltViewModel(),
    createEntryScreenViewModel: CreateEntryScreenViewModel = hiltViewModel(),
    audioFilePath: String? = null
) {
    // UI states from ViewModels
    val playbackUiState by playbackViewModel.uiState.collectAsState()
    val recordingUiState by recordingViewModel.uiState.collectAsState()
    val createEntryScreenUiState by createEntryScreenViewModel.uiState.collectAsState()

    // Bottom sheet state
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    val now = Date()
    val date = dateFormat.format(now)
    val timeStamp = timeFormat.format(now)

    val generateEntryId = (0..999999999).random()

    // Load audio file if not already loaded
    LaunchedEffect(audioFilePath) {
        if (!playbackUiState.isFileLoaded && audioFilePath != null) {
            Log.d("CreateEntryScreen", "Loading file: $audioFilePath")
            playbackViewModel.loadAudioFile(audioFilePath)
        }
    }

    // Debugging logs
    Log.d(
        "CreateEntryScreen",
        "UI State: isFileLoaded=${playbackUiState.isFileLoaded}, duration=${playbackUiState.duration}"
    )

    if (recordingUiState.showCancelRecordingDialog) {
        CustomAlertDialog(
            modifier = Modifier.width(300.dp),
            title = "Cancel Recording?",
            message = "You will lose your recording. If you're fine with that, press 'Leave'.",
            cancelButtonText = "Cancel",
            confirmButtonText = "Leave",
            onCancel = { recordingViewModel.onDismissLeave() },
            onConfirm = {
                recordingViewModel.onConfirmLeave()
                navController.navigateUp()
            }
        )
    }

    // Main UI background
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
                    onNavigationClick = {
                        recordingViewModel.onCancelRequest()
                    }
                )
            },
            bottomBar = { CreateEntryScreenBottomBar(
                    isSaveEnabled = createEntryScreenUiState.isSaveEnabled,
                    onCancel = { recordingViewModel.onCancelRequest() },
                    onSave = {
                        val newEntry = JournalEntry(
                            id = generateEntryId,
                            title = createEntryScreenUiState.addTitleTextFieldValue,
                            date = date,
                            timeStamp = timeStamp,
                            mood = createEntryScreenUiState.selectedMood!!,
                            description = createEntryScreenUiState.addDescriptionTextFieldValue,
                            topics = createEntryScreenUiState.selectedTopics.toList(),
                            audioFilePath = audioFilePath
                        )
                        journalHistoryViewModel.addJournalEntry(newEntry)
                        navController.navigateUp()
                    }
                )
            }
        ) { paddingValues ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                // Row for mood icon/button + Title field
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 8.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    MoodSelectionRow(
                        selectedMood = createEntryScreenUiState.selectedMood,
                        onOpenMoodSheet = { createEntryScreenViewModel.toggleMoodSheet(true) }
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    TitleField(
                        value = createEntryScreenUiState.addTitleTextFieldValue,
                        onValueChange = { createEntryScreenViewModel.updateTitle(it) }
                    )
                }
                // Show audio player if a file is loaded
                if (playbackUiState.isFileLoaded) {
                    Log.d(
                        "CreateEntryScreen",
                        "Displaying AudioPlayerBar with duration: ${playbackUiState.duration}"
                    )
                    AudioPlayer(
                        playbackUiState = playbackUiState,
                        onPlayPauseClicked = { playbackViewModel.togglePlayPause() },
                        onSeek = { playbackViewModel.seekToPosition(it) },
                        mood = createEntryScreenUiState.selectedMood
                    )
                    // Topic search + creation UI (for example)
                    TopicSearchAndCreate(
                        viewModel = topicViewModel,
                        selectedTopics = createEntryScreenUiState.selectedTopics.toList(),
                        onSelectedTopicsChange = { updatedList ->
                            createEntryScreenViewModel.selectTopics(updatedList)
                        }
                    )
                    // Description field
                    DescriptionField(
                        value = createEntryScreenUiState.addDescriptionTextFieldValue,
                        onValueChange = { createEntryScreenViewModel.updateDescription(it) }
                    )
                }
            }
        }
        // Display the mood bottom sheet if triggered
        if (createEntryScreenUiState.showMoodSheet) {
            ModalBottomSheet(
                onDismissRequest = { createEntryScreenViewModel.toggleMoodSheet(show = false) },
                sheetState = sheetState
            ) {
                // Pass callbacks so bottom sheet can confirm/cancel selection
                MoodBottomSheet(
                    moodOptions = setOf("Stressed", "Sad", "Neutral", "Peaceful", "Excited"),
                    onConfirm = { chosenMood ->
                        // Update 'selectedMood' in parent
                        createEntryScreenViewModel.selectMood(chosenMood)
                        // Close the sheet
                        createEntryScreenViewModel.toggleMoodSheet(show = false)
                    },
                    onCancel = {
                        createEntryScreenViewModel.toggleMoodSheet(show = false)
                    }
                )
            }
        }
    }
}



