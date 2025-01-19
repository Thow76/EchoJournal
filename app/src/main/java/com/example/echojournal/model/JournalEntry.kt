package com.example.echojournal.model

import kotlinx.serialization.*

@Serializable
data class JournalEntry(
    val id: Int,
    val title: String,
    val date: String,
    val timeStamp: String,
    val mood: String,
    val description: String,
    val audioFilePath: String? = null

)
