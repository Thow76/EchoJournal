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

    // Mutable state flow for the UI state
    private val _uiState = MutableStateFlow(JournalHistoryUiState())
    val uiState: StateFlow<JournalHistoryUiState> = _uiState

    // Simulates loading journal entries
    fun loadJournalEntries() {
        viewModelScope.launch {
            try {
                // Start loading
                _uiState.value = _uiState.value.copy(isLoading = true)

                // Simulated network delay
                delay(2000)

                // Load dummy journal entries
                val newEntries = getDummyJournalEntries()

                // Append new entries to the existing list
                _uiState.value = _uiState.value.copy(
                    journalEntries = _uiState.value.journalEntries + newEntries,
                    isLoading = false,
                    errorMessage = null
                )

            } catch (e: Exception) {
                // Handle errors
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Failed to load journal entries: ${e.message}"
                )
            }
        }
    }

    private fun getDummyJournalEntries(): List<JournalEntry> {
        return listOf(
            JournalEntry(1, "Morning Reflection", "Today", Mood.PEACEFUL, "A reflective morning."),
            JournalEntry(2, "Afternoon Thoughts", "Yesterday", Mood.NEUTRAL, "Casual thoughts on the day."),
            JournalEntry(3, "Evening Recap", "Saturday, Dec 28", Mood.SAD, "An emotional evening recap."),
            JournalEntry(4, "Weekend Adventure", "Last Weekend", Mood.EXCITED, "Explored a new hiking trail."),
            JournalEntry(5, "Work Musings", "Last Monday", Mood.STRESSED, "Busy day at work."),
            JournalEntry(6, "Relaxing Evening", "Last Friday", Mood.PEACEFUL, "Watched a movie and relaxed.")
        )
    }

    fun clearErrorMessage() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}
