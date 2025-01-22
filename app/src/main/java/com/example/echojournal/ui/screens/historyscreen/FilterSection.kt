package com.example.echojournal.ui.screens.historyscreen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.echojournal.ui.components.MutliOptionDropDownMenu.MultiSelectDropdownMenu
import com.example.echojournal.ui.screens.createentryscreen.TopicViewModel


@Composable
fun FilterSection(
    viewModel: JournalHistoryViewModel = viewModel(),
    topicViewModel: TopicViewModel = hiltViewModel()
) {
    val selectedMoods by viewModel.selectedMoods.collectAsState()
    val selectedTopics by viewModel.selectedTopics.collectAsState()

    // Get topics from the TopicViewModel UI state
    val topicUiState by topicViewModel.uiState.collectAsState()
    val storedTopics = topicUiState.allTopics

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        MultiSelectDropdownMenu(
            label = "Moods",
            options = listOf("Stressed", "Sad", "Neutral", "Peaceful", "Excited").sortedBy { (it.first()) },
            selectedOptions = selectedMoods,
            onOptionSelected = { chosen -> viewModel.addMoodFilter(chosen) },
            onOptionDeselected = { chosen -> viewModel.removeMoodFilter(chosen) },
            onClearSelection = { viewModel.clearAllMoodFilters() }
        )

        Spacer(modifier = Modifier.height(8.dp))

        MultiSelectDropdownMenu(
            label = "Topics",
            options = storedTopics.sortedBy { it.firstOrNull()?: ' ' },
            selectedOptions = selectedTopics,
            onOptionSelected = { chosen -> viewModel.addTopicFilter(chosen) },
            onOptionDeselected = { chosen -> viewModel.removeTopicFilter(chosen) },
            onClearSelection = { viewModel.clearAllTopicFilters() }
        )
    }
}




