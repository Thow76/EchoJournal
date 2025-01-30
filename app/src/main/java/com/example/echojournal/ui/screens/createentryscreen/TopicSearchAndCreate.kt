package com.example.echojournal.ui.screens.createentryscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.echojournal.ui.components.topics.TopicSuggestionsDropdown
import com.example.echojournal.viewmodels.TopicViewModel

@Composable
fun TopicSearchAndCreate(
    viewModel: TopicViewModel = hiltViewModel(),
    selectedTopics: List<String>,
    onSelectedTopicsChange: (List<String>) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val currentTopicsState = rememberUpdatedState(selectedTopics)

    LaunchedEffect(Unit) {
        // 2) Collect createTopicEvents once, but always read from currentTopicsState.value
        viewModel.createTopicEvents.collect { newlyCreatedTopic ->
            // This merges the newlyCreatedTopic into the *latest* selectedTopics
            val upToDateList = currentTopicsState.value + newlyCreatedTopic
            onSelectedTopicsChange(upToDateList)
        }
    }


    Column {
        // 1) Track or display the user’s topics
        MultiTopicSelectionRow(
            query = uiState.searchQuery,
            onQueryChange = viewModel::onSearchTextChange,
            selectedTopics = selectedTopics,
            onTopicSelectedOrCreated = { topic ->
                // Add new topic to parent's list
                onSelectedTopicsChange(selectedTopics + topic)
                // Reset text field
                viewModel.onSearchTextChange("")
            },
            onTopicRemove = { topicToRemove ->
                onSelectedTopicsChange(selectedTopics - topicToRemove)
            }
        )

        // 2) Suggestions
        if (uiState.showSuggestions) {
            TopicSuggestionsDropdown(
                topics = uiState.filteredTopics,
                query = uiState.searchQuery,
                onTopicSelected = { topic ->
                    onSelectedTopicsChange(selectedTopics + topic)
                    viewModel.onSearchTextChange("")
                },
                onCreateTopic = {
                    viewModel.onCreateTopic()
                    // After creation, the new topic is basically uiState.searchQuery
                }
            )
        }

        // 3) Loading/error states
        if (uiState.isCreating) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }
        uiState.errorMessage?.let { errorMessage ->
            Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
        }
    }
}





