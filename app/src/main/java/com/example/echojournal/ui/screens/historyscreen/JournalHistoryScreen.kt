package com.example.echojournal.ui.screens.historyscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import com.example.echojournal.data.JournalEntry
import com.example.echojournal.R
import com.example.echojournal.ui.components.CustomAppBar
import com.example.echojournal.ui.components.ErrorSnackbar
import com.example.echojournal.ui.components.LoadingIndicator
import com.example.echojournal.ui.theme.Gradients
import com.example.echojournal.ui.theme.MoodColors


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalHistoryScreen(
    navController: NavController,
    viewModel: JournalHistoryViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val selectedMoods by viewModel.selectedMoods.collectAsState()
    val selectedTopics by viewModel.selectedTopics.collectAsState()

    // Trigger loading of journal entries
    LaunchedEffect(Unit) {
        viewModel.loadJournalEntries()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Gradients.BgSaturateGradient)
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                CustomAppBar(title = stringResource(id = R.string.history_screen_heading))
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                FilterSection(
                    selectedMoods = selectedMoods,
                    selectedTopics = selectedTopics,
                    onMoodSelected = { viewModel.addMoodFilter(it) },
                    onMoodDeselected = { viewModel.removeMoodFilter(it) },
                    onTopicSelected = { viewModel.addTopicFilter(it) },
                    onTopicDeselected = { viewModel.removeTopicFilter(it) },
                    onClearMoodSelection = { viewModel.clearAllMoodFilters()},
                    onClearTopicSelection = { viewModel.clearAllTopicFilters()}
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
                                onDismiss = viewModel::clearErrorMessage
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
}


@Composable
fun AudioLogEntry(entry: JournalEntry) {
    val entryColour = when (entry.mood) {
        "Neutral" -> MoodColors.Neutral35
        "Stressed" -> MoodColors.Stressed35
        "Sad" -> MoodColors.Sad35
        "Peaceful" -> MoodColors.Peaceful35
        "Excited" -> MoodColors.Excited35
        else -> MaterialTheme.colorScheme.onSurfaceVariant
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),// Handle navigation on click
        contentAlignment = Alignment.CenterStart
    ) {
        Row {
            Icon(
                painter = painterResource(id = entry.iconResId!!),
                contentDescription = null,
                tint = Color.Unspecified,)
            Spacer(modifier = Modifier.width(8.dp))
            Row(modifier = Modifier.background(entryColour).fillMaxWidth()) {
                Column {
                    Text(
                        text = entry.date,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(text = entry.title, fontWeight = FontWeight.Bold)

                    Text(
                        text = entry.description,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

        }
    }
}


