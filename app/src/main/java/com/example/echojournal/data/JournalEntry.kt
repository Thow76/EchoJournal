package com.example.echojournal.data

import androidx.compose.ui.graphics.vector.ImageVector

data class JournalEntry(
    val id: Int,
    val iconResId: Int? = null,
    val title: String,
    val date: String, // Example: "Today", "Yesterday", or "Saturday, Dec 28"
    val timeStamp: String,
    val mood: String,
    val description: String
)


//
//enum class Mood { STRESSED, SAD, NEUTRAL, PEACEFUL, EXCITED }