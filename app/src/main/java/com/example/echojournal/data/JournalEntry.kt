package com.example.echojournal.data

data class JournalEntry(
    val id: Int,
    val title: String,
    val date: String, // Example: "Today", "Yesterday", or "Saturday, Dec 28"
    val mood: String,
    val description: String
)



enum class Mood { STRESSED, SAD, NEUTRAL, PEACEFUL, EXCITED }