package com.example.echojournal.di.repository

import com.example.echojournal.data.journalhistory.repository.JournalRepository
import com.example.echojournal.data.journalhistory.repository.JournalRepositoryImpl
import com.example.echojournal.data.journalhistory.datastore.journalHistoryDataStore
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object JournalHistoryRepositoryModule {

    @Provides
    @Singleton
    fun provideJournalHistoryRepository(
        @ApplicationContext context: Context): JournalRepository {
        return JournalRepositoryImpl(context.journalHistoryDataStore)
    }
}
