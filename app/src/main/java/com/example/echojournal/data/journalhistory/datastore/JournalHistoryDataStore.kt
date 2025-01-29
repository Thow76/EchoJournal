package com.example.echojournal.data.journalhistory.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.example.echojournal.model.JournalEntry

val Context.journalHistoryDataStore: DataStore<List<JournalEntry>> by dataStore(
    fileName = "journal_entries.json",
    serializer = JournalHistorySerializer
)
