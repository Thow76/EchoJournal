package com.example.echojournal.data.journalhistory.repository

import com.example.echojournal.model.JournalEntry
import kotlinx.coroutines.flow.Flow

interface JournalRepository {
    fun getAllJournalEntries(): Flow<List<JournalEntry>>
    suspend fun addJournalEntry(entry: JournalEntry)
    suspend fun removeJournalEntry(entryId: Int)
}
