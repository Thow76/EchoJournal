package com.example.echojournal.ui.screens.historyscreen

import com.example.echojournal.model.JournalEntry

data class JournalHistoryUiState(
    val allEntries: List<JournalEntry> = emptyList(),
    val journalEntries: List<JournalEntry> = emptyList(), // Existing entries
    val isLoading: Boolean = false, // Indicates if new entries are being loaded
    val addJournalEntryTitle: String = "", // Title for new entry
    val errorMessage: String? = null, // Error message, if any
)

