package com.example.echojournal.data.journalhistory.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.example.echojournal.model.JournalEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object JournalHistorySerializer : Serializer<List<JournalEntry>> {
    override val defaultValue: List<JournalEntry> = emptyList()

    override suspend fun readFrom(input: InputStream): List<JournalEntry> {
        return try {
            Json.decodeFromString(
                deserializer = ListSerializer(JournalEntry.serializer()),
                string = input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            throw CorruptionException("Error reading JournalEntry", e)
        }
    }

    override suspend fun writeTo(t: List<JournalEntry>, output: OutputStream) {
        withContext(Dispatchers.IO) {
            output.write(
                Json.encodeToString(
                    serializer = ListSerializer(JournalEntry.serializer()),
                    value = t
                ).encodeToByteArray()
            )
        }
    }
}
