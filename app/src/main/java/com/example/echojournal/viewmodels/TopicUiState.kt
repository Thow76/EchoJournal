package com.example.echojournal.viewmodels

data class TopicUiState(
    val searchQuery: String = "",
    val filteredTopics: List<String> = emptyList(),
    val showSuggestions: Boolean = false,
    val allTopics: List<String> = listOf("Android", "Compose", "Kotlin"),
    val isCreating: Boolean = false, // Indicates if a topic is being created
    val errorMessage: String? = null, // Holds any error messages
)
