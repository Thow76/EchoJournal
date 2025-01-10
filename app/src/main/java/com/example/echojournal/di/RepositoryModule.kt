package com.example.echojournal.di

import com.example.echojournal.data.JournalRepository
import com.example.echojournal.data.JournalRepositoryImpl
import com.example.echojournal.data.journalEntryDataStore
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideJournalRepository(
        @ApplicationContext context: Context): JournalRepository {
        return JournalRepositoryImpl(context.journalEntryDataStore)
    }
}
