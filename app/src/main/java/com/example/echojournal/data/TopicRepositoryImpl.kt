package com.example.echojournal.data

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TopicRepositoryImpl(
    private val dataStore: DataStore<List<String>>
) : TopicRepository {

    // Returns a Flow of the current topic list
    override fun getAllTopics(): Flow<List<String>> {
        return dataStore.data.map { it }
    }

    // Adds a single topic to the list
    override suspend fun addTopic(topic: String) {
        dataStore.updateData { currentTopics ->
            // Only add if not already in the list (optional check)
            if (!currentTopics.contains(topic)) {
                currentTopics + topic
            } else {
                currentTopics
            }
        }
    }

    // Removes a single topic
    override suspend fun removeTopic(topic: String) {
        dataStore.updateData { currentTopics ->
            currentTopics.filter { it != topic }
        }
    }
}
