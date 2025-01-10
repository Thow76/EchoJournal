package com.example.echojournal.di

import android.content.Context
import androidx.datastore.dataStore
import com.example.echojournal.data.JournalEntrySerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    private val Context.journalEntryDataStore by dataStore(
        fileName = "journal_entries.json",
        serializer = JournalEntrySerializer
    )

    @Provides
    fun provideJournalEntryDataStore(context: Context) = context.journalEntryDataStore
}
