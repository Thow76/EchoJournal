package com.example.echojournal.viewmodels

data class CreateRecordScreenUiState(
    val addTitleTextFieldValue: String = "",
    val addDescriptionTextFieldValue: String = "",
    val selectedMood: String? = null,
    val selectedTopics: List<String> = emptyList(),
    val isSaveEnabled: Boolean = false,
    val showMoodSheet: Boolean = false,
)

