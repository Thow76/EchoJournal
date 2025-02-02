package com.example.echojournal.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.echojournal.R
import com.example.echojournal.data.topics.repository.TopicRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopicViewModel @Inject constructor(
    private val topicRepository: TopicRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    // Initialize the UI state with default values
    private val _uiState = MutableStateFlow(TopicUiState())
    val uiState: StateFlow<TopicUiState> = _uiState.asStateFlow()
    private val _createTopicEvents = MutableSharedFlow<String>()
    val createTopicEvents: SharedFlow<String> = _createTopicEvents

    init {
        // 1) Collect the repository flow to keep allTopics in sync with persistent storage
        viewModelScope.launch {
            topicRepository.getAllTopics().collect { storedTopics ->
                _uiState.update { currentState ->
                    currentState.copy(allTopics = storedTopics)
                }
            }
        }

        // 2) Observe changes to searchQuery + allTopics to update filteredTopics and showSuggestions
        viewModelScope.launch {
            _uiState
                .map { it.searchQuery }
                .combine(_uiState.map { it.allTopics }) { query, topics ->
                    if (query.isBlank()) {
                        emptyList()
                    } else {
                        topics.filter { it.contains(query, ignoreCase = true) }
                    }
                }
                .collect { filteredTopics ->
                    _uiState.update { currentState ->
                        currentState.copy(filteredTopics = filteredTopics)
                    }
                }
        }
    }

    /**
     * Updates the search query in the UI state.
     * Triggers the recomputation of filteredTopics and showSuggestions via the init block.
     */
    fun onSearchTextChange(newQuery: String) {
        _uiState.update { currentState ->
            currentState.copy(
                searchQuery = newQuery,
                // Show only if new query is not blank
                showSuggestions = newQuery.isNotBlank(),
                errorMessage = null
            )
        }
    }

    /**
     * Initiates the creation of a new topic based on the current search query.
     * Checks for duplicates, then stores the topic via the repository.
     * Handles loading and error states appropriately.
     */

    fun onCreateTopic() {
        val query = _uiState.value.searchQuery.trim()
        
        viewModelScope.launch {
            _uiState.update { it.copy(isCreating = true, errorMessage = null) }
            try {
                // Insert the new topic into your repository or database
                topicRepository.addTopic(query)

                // After successful creation, reset UI state (e.g., clear search field)
                _uiState.update {
                    it.copy(
                        isCreating = false,
                        searchQuery = "",
                        showSuggestions = false
                    )
                }

                // Emit the newly created topic so the UI can react
                _createTopicEvents.emit(query)

            } catch (e: Exception) {
                // If creation fails, report an error in the UI state
                _uiState.update {
                    it.copy(
                        isCreating = false,
                        errorMessage = context.getString(
                            R.string.error_message_failed_to_create_topic,
                            e.message
                        )
                    )
                }
            }
        }
    }
}



