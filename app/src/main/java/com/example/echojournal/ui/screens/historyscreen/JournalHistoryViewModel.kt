package com.example.echojournal.ui.screens.historyscreen

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.echojournal.R
import com.example.echojournal.data.JournalEntry
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

    private val expandedStates = mutableStateMapOf<Int, Boolean>()

    fun loadJournalEntries() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)
                delay(1000) // Simulate loading delay
                val newEntries = getDummyJournalEntries()
                // We store the new “raw” list
                _uiState.value = _uiState.value.copy(
                    allEntries = newEntries,
                    isLoading = false,
                    errorMessage = null)
                // Then filter them
                applyFilters()
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
            JournalEntry(
                id = 1,
                title = "Family",
                date = "Today", // Remains "Today"
                mood = "Peaceful",
                description = "A reflective morning.",
                iconResId = R.drawable.peaceful_mood,
                timeStamp = "09:00 AM"
            ),
            JournalEntry(
                id = 2,
                title = "Friends",
                date = "Yesterday", // Remains "Yesterday"
                mood = "Neutral",
                description = """
                Lorem ipsum dolor sit amet, consectetur adipiscing elit.
                Suspendisse eu erat quis libero lacinia imperdiet.
                Maecenas vehicula nisl sit amet feugiat porta.
                Integer mollis enim quis imperdiet consequat.
            """.trimIndent(),
                iconResId = R.drawable.neutral_mood,
                timeStamp = "07:15 PM"
            ),
            JournalEntry(
                id = 3,
                title = "Love",
                // Previously "Saturday" -> Now in the style: "Saturday, Dec 28"
                date = "Saturday, Dec 28",
                mood = "Sad",
                description = "An emotional evening recap.",
                iconResId = R.drawable.sad_mood,
                timeStamp = "10:30 PM"
            ),
            JournalEntry(
                id = 4,
                title = "Surprise",
                // Previously "Last Weekend" -> Example: "Sunday, Dec 29"
                date = "Sunday, Dec 29",
                mood = "Excited",
                description = "Explored a new hiking trail.",
                iconResId = R.drawable.excited_mood,
                timeStamp = "02:00 PM"
            ),
            JournalEntry(
                id = 5,
                title = "Work",
                // Previously "Last Monday" -> Example: "Monday, Dec 30"
                date = "Monday, Dec 30",
                mood = "Stressed",
                description = "Busy day at work.",
                iconResId = R.drawable.stressed_mood,
                timeStamp = "11:45 AM"
            ),
            JournalEntry(
                id = 6,
                title = "Work",
                // Previously "Last Friday" -> Example: "Friday, Dec 27"
                date = "Friday, Dec 27",
                mood = "Peaceful",
                description = "Watched a movie and relaxed.",
                iconResId = R.drawable.peaceful_mood,
                timeStamp = "09:30 PM"
            )
        )
    }

    fun isExpanded(id: Int): Boolean {
        return expandedStates[id] ?: false
    }

    fun toggleExpanded(id: Int) {
        expandedStates[id] = !(expandedStates[id] ?: false)
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
        val all = _uiState.value.allEntries
        val filtered = all.filter { entry ->
            (_selectedMoods.value.isEmpty() || entry.mood in _selectedMoods.value) &&
                    (_selectedTopics.value.isEmpty() ||
                            _selectedTopics.value.any { topic -> entry.title.contains(topic, ignoreCase = true) })
        }

        _uiState.value = _uiState.value.copy(journalEntries = filtered)
    }


    fun clearErrorMessage() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}
