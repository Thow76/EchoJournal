package com.example.echojournal.viewmodels

import android.content.Context
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.echojournal.R
import com.example.echojournal.model.JournalEntry
import com.example.echojournal.data.journalhistory.repository.JournalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JournalHistoryViewModel @Inject constructor(
    private val repository: JournalRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {
    

    private val _uiState = MutableStateFlow(JournalHistoryUiState())
    val uiState: StateFlow<JournalHistoryUiState> = _uiState

    private val _selectedMoods = MutableStateFlow<Set<String>>(emptySet())
    private val _selectedTopics = MutableStateFlow<Set<String>>(emptySet())

    val selectedMoods: StateFlow<Set<String>> = _selectedMoods
    val selectedTopics: StateFlow<Set<String>> = _selectedTopics

    private val expandedStates = mutableStateMapOf<Int, Boolean>()

    val journalEntries: StateFlow<List<JournalEntry>> = repository
        .getAllJournalEntries()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addJournalEntry(entry: JournalEntry) {
        viewModelScope.launch {
            try {
                repository.addJournalEntry(entry)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = context.getString(
                        R.string.error_message_failed_to_add_journal_entry,
                        e.message
                    )
                )
            }
        }
    }

    fun loadJournalEntries() {
        viewModelScope.launch {
            try {
                // Update UI state to indicate loading
                _uiState.value = _uiState.value.copy(isLoading = true)

                // Collect journal entries from the repository
                val entries = repository.getAllJournalEntries().firstOrNull() ?: emptyList()

               // initializeExpandedStates(entries)

                // Update UI state with all entries
                _uiState.value = _uiState.value.copy(
                    allEntries = entries,
                    isLoading = false,
                    errorMessage = null
                )

                // Apply any active filters
                applyFilters()
            } catch (e: Exception) {
                // Handle errors and update UI state
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = context.getString(
                        R.string.error_message_failed_to_load_journal_entries,
                        e.message
                    )
                )
            }
        }
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
                            entry.topics.any { topicInEntry ->
                                _selectedTopics.value.any { selectedTopic ->
                                    topicInEntry.equals(selectedTopic, ignoreCase = true)
                                }
                            }
                            )
        }
        _uiState.value = _uiState.value.copy(journalEntries = filtered)
    }

    fun clearErrorMessage() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}
