package com.example.echojournal.ui.screens.createentryscreen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CreateEntryScreenViewModel @Inject constructor(
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateEntryScreenUiState())
    val uiState: StateFlow<CreateEntryScreenUiState> = _uiState.asStateFlow()

    // Functions to update state
    fun updateTitle(value: String) {
        _uiState.update { it.copy(addTitleTextFieldValue = value, isSaveEnabled = validateSave()) }
    }

    fun updateDescription(value: String) {
        _uiState.update { it.copy(addDescriptionTextFieldValue = value) }
    }

    fun selectMood(mood: String) {
        _uiState.update { it.copy(selectedMood = mood, isSaveEnabled = validateSave()) }
    }

    fun toggleMoodSheet(show: Boolean) {
        _uiState.update { it.copy(showMoodSheet = show) }
    }

    fun selectTopics(topics: List<String>) {
        _uiState.update { it.copy(selectedTopics = topics) }
    }

    private fun validateSave(): Boolean {
        return !_uiState.value.addTitleTextFieldValue.isBlank() && _uiState.value.selectedMood != null
    }
}
