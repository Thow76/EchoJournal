package com.example.echojournal.data.topics.repository

import kotlinx.coroutines.flow.Flow

interface TopicRepository {
    fun getAllTopics(): Flow<List<String>>
    suspend fun addTopic(topic: String)
    suspend fun removeTopic(topic: String)
}
