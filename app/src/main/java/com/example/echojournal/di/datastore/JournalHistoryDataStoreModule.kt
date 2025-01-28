package com.example.echojournal.di.datastore

import android.content.Context
import androidx.datastore.dataStore
import com.example.echojournal.data.journalhistory.datastore.JournalHistorySerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object JournalHistoryDataStoreModule {

    private val Context.journalHistoryDataStore by dataStore(
        fileName = "journal_entries.json",
        serializer = JournalHistorySerializer
    )

    @Provides
    fun provideJournalHistoryDataStore(context: Context) = context.journalHistoryDataStore
}
