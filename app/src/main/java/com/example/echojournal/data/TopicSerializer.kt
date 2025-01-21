package com.example.echojournal.data

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object TopicSerializer : Serializer<List<String>> {

    // Define the default empty list
    override val defaultValue: List<String> = emptyList()

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun readFrom(input: InputStream): List<String> {
        return try {
            // Decode a JSON array of strings, e.g. ["Android", "Compose", "Kotlin"]
            Json.decodeFromString(
                deserializer = ListSerializer(String.serializer()),
                string = input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            throw CorruptionException("Error reading topic list from DataStore.", e)
        }
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun writeTo(
        t: List<String>,
        output: OutputStream
    ) {
        // Encode list of strings into JSON
        output.write(
            Json.encodeToString(
                serializer = ListSerializer(String.serializer()),
                value = t
            ).encodeToByteArray()
        )
    }
}
