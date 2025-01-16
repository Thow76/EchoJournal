package com.example.echojournal.ui.createentryscreen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.echojournal.ui.components.CustomAppBar
import com.example.echojournal.ui.theme.Gradients
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.echojournal.ui.components.AudioPlayerBar
import com.example.echojournal.ui.components.CustomGradientIconButton
import com.example.echojournal.ui.components.CustomTextField
import com.example.echojournal.ui.components.MutliOptionDropDownMenu.getMoodIcon
import com.example.echojournal.ui.screens.historyscreen.JournalHistoryViewModel
import com.example.echojournal.ui.screens.recordscreen.PlaybackViewModel
import com.example.echojournal.ui.components.TopicSearchAndCreate
import com.example.echojournal.ui.theme.MaterialColors
import com.example.echojournal.ui.theme.Palettes
import com.google.accompanist.permissions.ExperimentalPermissionsApi

//@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
//@Composable
//fun CreateEntryScreen(
//    navController: NavController,
//    journalHistoryViewModel: JournalHistoryViewModel = hiltViewModel(),
//    playbackViewModel: PlaybackViewModel = hiltViewModel(),
//    topicViewModel: TopicViewModel = hiltViewModel(),
//    audioFilePath: String? = null
//) {
//    val journalUiState by journalHistoryViewModel.uiState.collectAsState()
//    val playbackUiState by playbackViewModel.uiState.collectAsState()
//
//    val showMoodSheet = remember { mutableStateOf(false) }
//
//    val sheetState = rememberModalBottomSheetState(
//        skipPartiallyExpanded = true
//    )
//    // State to hold the text field value
//    var addTitleTextFieldValue by remember { mutableStateOf("") }
//    var addDescriptionTextFieldValue by remember { mutableStateOf("") }
//
//    // For simplicity, here’s a hardcoded mutable list:
//    val topics = remember { mutableStateListOf("Android", "Compose", "Kotlin") }
//
//    // Debug logs
//    LaunchedEffect(audioFilePath) {
//        if (!playbackUiState.isFileLoaded && audioFilePath != null) {
//            Log.d("CreateEntryScreen", "Loading file: $audioFilePath")
//            playbackViewModel.loadAudioFile(audioFilePath)
//        }
//    }
//
//    Log.d(
//        "CreateEntryScreen",
//        "UI State: isFileLoaded=${playbackUiState.isFileLoaded}, duration=${playbackUiState.duration}"
//    )
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(brush = Gradients.BgSaturateGradient)
//    ) {
//        Scaffold(
//            snackbarHost = { SnackbarHost(hostState = remember { SnackbarHostState() }) },
//            containerColor = Color.White,
//            topBar = {
//                CustomAppBar(
//                    title = "New Entry",
//                    onNavigationClick = {navController.navigateUp() }
//                )
//                     },
//        ) { paddingValues ->
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(paddingValues)
//            ) {
//               Row(modifier = Modifier
//                   .fillMaxWidth()
//                   .padding(start = 16.dp, end = 8.dp),
//                    horizontalArrangement = Arrangement.Start,
//                   verticalAlignment = Alignment.CenterVertically
//                   ) {
//                   CustomGradientIconButton(
//                       modifier = Modifier.size(32.dp),
//                       onClick = {showMoodSheet.value = true},
//                       icon = {
//                           Icon(modifier = Modifier.size(24.dp),
//                               imageVector = Icons.Default.Add,
//                               contentDescription = "Record",
//                               tint = Palettes.Secondary70) },
//                        contentDescription = "Record a new entry",
//                       buttonGradient = Gradients.BgSaturateGradient
//
//                   )
//                   CustomTextField(
//                       value = addTitleTextFieldValue,
//                       onValueChange = { newValue -> addTitleTextFieldValue = newValue },
//                       placeholderText = "Add Title...",
//                       modifier = Modifier.padding(0.dp),
//                       textStyle = MaterialTheme.typography.headlineLarge.copy(
//                           textAlign = TextAlign.Start
//                       ),
//                       leadingIcon = null,
//                       placeholderColor = MaterialColors.OutlineVariantNeutralVariant80,
//                       containerColor = Color.Transparent
//                   )
//                }
//                // Show Audio Player if a file is loaded
//                if (playbackUiState.isFileLoaded) {
//                    Log.d(
//                        "CreateEntryScreen",
//                        "Displaying AudioPlayerBar with duration: ${playbackUiState.duration}"
//                    )
//                    playbackUiState.duration?.let { duration ->
//                        key(duration) {
//                            AudioPlayerBar(
//                                isPlaying = playbackUiState.isPlaybackActive,
//                                currentPosition = playbackUiState.currentPosition,
//                                duration = duration,
//                                onPlayPauseClicked = { playbackViewModel.togglePlayPause() },
//                                onSeek = { playbackViewModel.seekToPosition(it) },
//                            )
//                        }
//
//                    }
//
//                }
//                TopicSearchAndCreate (viewModel = topicViewModel)
//                CustomTextField(
//                    value = addDescriptionTextFieldValue,
//                    onValueChange = { newValue -> addDescriptionTextFieldValue = newValue },
//                    placeholderText = "Add Description...",
//                    modifier = Modifier.padding(start = 16.dp),
//                    textStyle = MaterialTheme.typography.bodyLarge.copy(
//                        textAlign = TextAlign.Start
//                    ),
//                    leadingIcon = {
//                        Icon(
//                            modifier =  Modifier.size(18.dp),
//                            imageVector = Icons.Default.Edit,
//                            contentDescription = "Add Description",
//                            tint = MaterialColors.OutlineVariantNeutralVariant80
//                            )
//                    },
//                    placeholderColor = MaterialColors.OutlineVariantNeutralVariant80,
//                    placeholderStyle = MaterialTheme.typography.bodyLarge,
//                    containerColor = Color.Transparent
//                )
//            }
//        }
//    }
//    if (showMoodSheet.value) {
//        ModalBottomSheet(
//            onDismissRequest = { showMoodSheet.value = false },
//            sheetState = sheetState
//        ) {
//            MoodBottomSheet(
//                moodOptions = setOf("Stressed", "Sad", "Neutral", "Peaceful", "Excited")
//            )
//        }
//}
//}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun CreateEntryScreen(
    navController: NavController,
    journalHistoryViewModel: JournalHistoryViewModel = hiltViewModel(),
    playbackViewModel: PlaybackViewModel = hiltViewModel(),
    topicViewModel: TopicViewModel = hiltViewModel(),
    audioFilePath: String? = null
) {
    // UI states from ViewModels
    val journalUiState by journalHistoryViewModel.uiState.collectAsState()
    val playbackUiState by playbackViewModel.uiState.collectAsState()

    // Controls visibility of the MoodBottomSheet
    val showMoodSheet = remember { mutableStateOf(false) }

    // Holds the mood chosen from the MoodBottomSheet
    var selectedMood by remember { mutableStateOf<String?>(null) }

    // Bottom sheet state
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    // Text fields
    var addTitleTextFieldValue by remember { mutableStateOf("") }
    var addDescriptionTextFieldValue by remember { mutableStateOf("") }

    // Topics (example usage)
    val topics = remember { mutableStateListOf("Android", "Compose", "Kotlin") }

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
                    onNavigationClick = { navController.navigateUp() }
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
                    // Show either the "Add Mood" button or the selected mood icon
                    if (selectedMood == null) {
                        // No mood selected yet → Show button to pick a mood
                        CustomGradientIconButton(
                            modifier = Modifier.size(32.dp),
                            onClick = { showMoodSheet.value = true },
                            icon = {
                                Icon(
                                    modifier = Modifier.size(24.dp),
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Choose Mood",
                                    tint = Palettes.Secondary70
                                )
                            },
                            contentDescription = "Choose Mood",
                            buttonGradient = Gradients.BgSaturateGradient
                        )
                    } else {
                        // A mood has been selected → Show the mood icon
                        Icon(
                            modifier = Modifier
                                .size(32.dp)
                                .clickable {
                                    // Optionally let user change their mood
                                    showMoodSheet.value = true
                                },
                            imageVector = getMoodIcon(selectedMood!!),
                            contentDescription = "Selected Mood",
                            tint = Color.Unspecified
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    // Title field
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

                // Show audio player if a file is loaded
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

                // Topic search + creation UI (for example)
                TopicSearchAndCreate(viewModel = topicViewModel)

                // Description field
                CustomTextField(
                    value = addDescriptionTextFieldValue,
                    onValueChange = { newValue -> addDescriptionTextFieldValue = newValue },
                    placeholderText = "Add Description...",
                    modifier = Modifier.padding(start = 16.dp),
                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                        textAlign = TextAlign.Start
                    ),
                    leadingIcon = {
                        Icon(
                            modifier = Modifier.size(18.dp),
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Add Description",
                            tint = MaterialColors.OutlineVariantNeutralVariant80
                        )
                    },
                    placeholderColor = MaterialColors.OutlineVariantNeutralVariant80,
                    placeholderStyle = MaterialTheme.typography.bodyLarge,
                    containerColor = Color.Transparent
                )
            }
        }
    }

    // Display the mood bottom sheet if triggered
    if (showMoodSheet.value) {
        ModalBottomSheet(
            onDismissRequest = { showMoodSheet.value = false },
            sheetState = sheetState
        ) {
            // Pass callbacks so bottom sheet can confirm/cancel selection
            MoodBottomSheet(
                moodOptions = setOf("Stressed", "Sad", "Neutral", "Peaceful", "Excited"),
                onConfirm = { chosenMood ->
                    // Update 'selectedMood' in parent
                    selectedMood = chosenMood
                    // Close the sheet
                    showMoodSheet.value = false
                },
                onCancel = {
                    // Optional: Clear the mood or keep the old selection
                    // selectedMood = null
                    showMoodSheet.value = false
                }
            )
        }
    }
}




