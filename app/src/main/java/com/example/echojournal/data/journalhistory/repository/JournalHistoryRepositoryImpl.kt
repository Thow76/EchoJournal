package com.example.echojournal.data.journalhistory.repository

import androidx.datastore.core.DataStore
import com.example.echojournal.model.JournalEntry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class JournalRepositoryImpl(
    private val dataStore: DataStore<List<JournalEntry>>
) : JournalRepository {

    override fun getAllJournalEntries(): Flow<List<JournalEntry>> {
        return dataStore.data.map { it }
    }

    override suspend fun addJournalEntry(entry: JournalEntry) {
        dataStore.updateData { currentEntries ->
            currentEntries + entry
        }
    }

    override suspend fun removeJournalEntry(entryId: Int) {
        dataStore.updateData { currentEntries ->
            currentEntries.filter { it.id != entryId }
        }
    }


}
