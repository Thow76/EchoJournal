package com.example.echojournal.ui.createentryscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class TopicViewModel @Inject constructor() : ViewModel() {

    private val _allTopics = MutableStateFlow(listOf("Android", "Compose", "Kotlin"))
    // Exposed read-only for outside
    val allTopics: StateFlow<List<String>> = _allTopics.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // We can decide to show suggestions if query is not blank
    val showSuggestions = searchQuery.map { it.isNotBlank() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            false
        )

    // Filter topics whenever _searchQuery or _allTopics change
    val filteredTopics = combine(_searchQuery, _allTopics) { query, topics ->
        // If the query is blank, return an empty list
        // (or all topics if you prefer)
        if (query.isBlank()) {
            emptyList()
        } else {
            topics.filter { it.contains(query, ignoreCase = true) }
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    // Callback from UI
    fun onSearchTextChange(newQuery: String) {
        _searchQuery.value = newQuery
    }

    fun onTopicSelected(selected: String) {
        // Possibly do something else
        _searchQuery.value = selected
    }

    fun onCreateTopic() {
        val query = _searchQuery.value
        if (query.isNotBlank()) {
            // Add to the list if not present
            val updated = _allTopics.value.toMutableList().apply {
                add(query)
            }
            _allTopics.value = updated
        }
    }
}
