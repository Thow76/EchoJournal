package com.example.echojournal.data.topics.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TopicDataStoreModule {

    private val Context.topicDataStore by dataStore(
        fileName = "topics.json",
        serializer = TopicSerializer
    )

    @Provides
    @Singleton
    fun provideTopicDataStore(
        @ApplicationContext context: Context
    ): DataStore<List<String>> {
        return context.topicDataStore
    }
}
