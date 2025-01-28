package com.example.echojournal.di.repository

import androidx.datastore.core.DataStore
import com.example.echojournal.data.topics.repository.TopicRepository
import com.example.echojournal.data.topics.repository.TopicRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TopicRepositoryModule {

    @Provides
    @Singleton
    fun provideTopicRepository(
        topicDataStore: DataStore<List<String>>
    ): TopicRepository {
        return TopicRepositoryImpl(topicDataStore)
    }
}
