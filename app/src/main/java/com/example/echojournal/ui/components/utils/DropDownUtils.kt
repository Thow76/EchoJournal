package com.example.echojournal.ui.components.utils

import androidx.compose.runtime.MutableState

// Helper to get selected text
fun getSelectedText(
    label: String,
    selectedOptions: Set<String>,
    options: List<String>
): String {
    return if (selectedOptions.size == options.size) {
        "All $label"
    } else {
        selectedOptions.sorted()
            .joinToString(limit = 2, truncated = " +${selectedOptions.size - 2}")
            .ifEmpty { "All $label" }
    }
}

// Helper for dropdown toggle logic
fun handleDropdownToggle(
    isAllSelected: Boolean,
    onClearSelection: () -> Unit,
    expanded: MutableState<Boolean>,
    toggleAfterReset: MutableState<Boolean>
) {
    if (isAllSelected) {
        onClearSelection()
        expanded.value = false
        toggleAfterReset.value = true
    } else {
        expanded.value = !expanded.value
    }
}