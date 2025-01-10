package com.example.echojournal.data

import androidx.datastore.core.DataStore
import com.example.echojournal.model.JournalEntry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun DataStore<List<JournalEntry>>.getJournalEntries(): Flow<List<JournalEntry>> {
    return this.data.map { it }
}

suspend fun DataStore<List<JournalEntry>>.addJournalEntry(entry: JournalEntry) {
    this.updateData { currentEntries ->
        currentEntries + entry
    }
}

suspend fun DataStore<List<JournalEntry>>.removeJournalEntry(entryId: Int) {
    this.updateData { currentEntries ->
        currentEntries.filter { it.id != entryId }
    }
}






