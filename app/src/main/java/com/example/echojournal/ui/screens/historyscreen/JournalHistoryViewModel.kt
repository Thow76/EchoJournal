package com.example.echojournal.ui.screens.historyscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.echojournal.data.JournalEntry
import com.example.echojournal.data.Mood
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class JournalHistoryViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(JournalHistoryUiState())
    val uiState: StateFlow<JournalHistoryUiState> = _uiState

    private val _selectedMoods = MutableStateFlow<Set<String>>(emptySet())
    private val _selectedTopics = MutableStateFlow<Set<String>>(emptySet())

    val selectedMoods: StateFlow<Set<String>> = _selectedMoods
    val selectedTopics: StateFlow<Set<String>> = _selectedTopics

    fun loadJournalEntries() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)
                delay(2000)
                _uiState.value = _uiState.value.copy(
                    journalEntries = getDummyJournalEntries(),
                    isLoading = false,
                    errorMessage = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Failed to load journal entries: ${e.message}"
                )
            }
        }
    }

    private fun getDummyJournalEntries(): List<JournalEntry> {
        return listOf(
            JournalEntry(1, "Family", "Today", "Peaceful", "A reflective morning."),
            JournalEntry(2, "Friends", "Yesterday", "Neutral", "Casual thoughts on the day."),
            JournalEntry(3, "Love", "Saturday, Dec 28", "Sad", "An emotional evening recap."),
            JournalEntry(4, "Surprise", "Last Weekend", "Excited", "Explored a new hiking trail."),
            JournalEntry(5, "Work", "Last Monday", "Stressed", "Busy day at work."),
            JournalEntry(6, "Work", "Last Friday", "Peaceful", "Watched a movie and relaxed.")
        )
    }

    fun addMoodFilter(mood: String) {
        _selectedMoods.value = _selectedMoods.value + mood
        applyFilters()
    }

    fun removeMoodFilter(mood: String) {
        _selectedMoods.value = _selectedMoods.value - mood
        applyFilters()
    }

    fun addTopicFilter(topic: String) {
        _selectedTopics.value = _selectedTopics.value + topic
        applyFilters()
    }

    fun removeTopicFilter(topic: String) {
        _selectedTopics.value = _selectedTopics.value - topic
        applyFilters()
    }

    // Clears all mood filters
    fun clearAllMoodFilters() {
        _selectedMoods.value = emptySet()
        applyFilters()
    }

    // Clears all topic filters
    fun clearAllTopicFilters() {
        _selectedTopics.value = emptySet()
        applyFilters()
    }

    private fun applyFilters() {
        val allEntries = getDummyJournalEntries() // Fetch all available journal entries

        val filteredEntries = allEntries.filter { entry ->
            // Filter by moods if any are selected
            (_selectedMoods.value.isEmpty() || entry.mood in _selectedMoods.value) &&
                    // Filter by topics if any are selected
                    (_selectedTopics.value.isEmpty() || _selectedTopics.value.any { topic -> entry.title.contains(topic, ignoreCase = true) })
        }

        // Update the UI state with the filtered entries
        _uiState.value = _uiState.value.copy(journalEntries = filteredEntries)
    }

    fun clearErrorMessage() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}
