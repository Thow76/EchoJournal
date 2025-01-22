package com.example.echojournal.ui.screens.createentryscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.echojournal.data.TopicRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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

//@HiltViewModel
//class TopicViewModel @Inject constructor(
//    private val topicRepository: TopicRepository):
//    ViewModel() {
//
//    // Initialize the UI state with default values
//    private val _uiState = MutableStateFlow(TopicUiState())
//    val uiState: StateFlow<TopicUiState> = _uiState.asStateFlow()
//
//    init {
//        // Observe changes to searchQuery and allTopics to update filteredTopics and showSuggestions
//        viewModelScope.launch {
//            _uiState
//                .map { it.searchQuery }
//                .combine(_uiState.map { it.allTopics }) { query, topics ->
//                    val filtered = if (query.isBlank()) {
//                        emptyList()
//                    } else {
//                        topics.filter { it.contains(query, ignoreCase = true) }
//                    }
//                    // Remove the "val show = query.isNotBlank()" line
//
//                    // Just return the filtered list, and let the UI or other events decide
//                    filtered
//                }
//                .collect { filteredTopics ->
//                    _uiState.update { currentState ->
//                        currentState.copy(
//                            filteredTopics = filteredTopics,
//                            // Do not override showSuggestions here
//                            // Let your manual calls control it
//                        )
//                    }
//                }
//        }
//    }
//
//    /**
//     * Updates the search query in the UI state.
//     * Triggers the recomputation of filteredTopics and showSuggestions via the init block.
//     */
//    fun onSearchTextChange(newQuery: String) {
//        _uiState.update { currentState ->
//            currentState.copy(
//                searchQuery = newQuery,
//                // Show only if new query is not blank
//                showSuggestions = newQuery.isNotBlank(),
//                errorMessage = null
//            )
//        }
//    }
//
//    /**
//     * Handles the selection of a topic.
//     * Sets the search query to the selected topic and hides suggestions.
//     */
//
//    fun onTopicSelected(selected: String) {
//        _uiState.update { currentState ->
//            currentState.copy(
//                searchQuery = selected,
//                showSuggestions = false
//            )
//        }
//    }
//
//
//    /**
//     * Initiates the creation of a new topic based on the current search query.
//     * Adds the new topic to allTopics if it doesn't already exist.
//     * Handles loading and error states appropriately.
//     */
//    fun onCreateTopic() {
//        val query = _uiState.value.searchQuery.trim()
//        if (query.isBlank()) {
//            _uiState.update { it.copy(errorMessage = "Topic name cannot be empty.") }
//            return
//        }
//
//        // Check if the topic already exists (case-insensitive)
//        val exists = _uiState.value.allTopics.any { it.equals(query, ignoreCase = true) }
//        if (exists) {
//            _uiState.update { it.copy(errorMessage = "Topic already exists.") }
//            return
//        }
//
//        viewModelScope.launch {
//            _uiState.update { it.copy(isCreating = true, errorMessage = null) }
//
//            try {
//                // Simulate a network/database operation with a delay
//                // Replace this with actual creation logic as needed
//                kotlinx.coroutines.delay(1000)
//
//                // Update the list of all topics
//                val updatedTopics = _uiState.value.allTopics + query
//                _uiState.update { currentState ->
//                    currentState.copy(
//                        allTopics = updatedTopics,
//                        searchQuery = query,
//                        isCreating = false,
//                        showSuggestions = false
//                    )
//                }
//            } catch (e: Exception) {
//                // Handle any errors that occur during creation
//                _uiState.update { it.copy(isCreating = false, errorMessage = "Failed to create topic.") }
//            }
//        }
//    }
//}

@HiltViewModel
class TopicViewModel @Inject constructor(
    private val topicRepository: TopicRepository
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
     * Handles the selection of a topic.
     * Sets the search query to the selected topic and hides suggestions.
     */
    fun onTopicSelected(selected: String) {
        _uiState.update { currentState ->
            currentState.copy(
                searchQuery = selected,
                showSuggestions = false
            )
        }
    }

    // Optional: directly expose the repository flow.
    fun getAllTopics(): Flow<List<String>> {
        return topicRepository.getAllTopics()
    }

    /**
     * Initiates the creation of a new topic based on the current search query.
     * Checks for duplicates, then stores the topic via the repository.
     * Handles loading and error states appropriately.
     */

    fun onCreateTopic() {
        val query = _uiState.value.searchQuery.trim()

        // 1) Basic validation
        if (query.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Topic name cannot be empty.") }
            return
        }

        // 2) Check for duplicates in current allTopics (case-insensitive)
        val exists = _uiState.value.allTopics.any { it.equals(query, ignoreCase = true) }
        if (exists) {
            _uiState.update { it.copy(errorMessage = "Topic already exists.") }
            return
        }

        // 3) Launch a coroutine to create the topic asynchronously
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
                        errorMessage = "Failed to create topic: ${e.message}"
                    )
                }
            }
        }
    }
}



