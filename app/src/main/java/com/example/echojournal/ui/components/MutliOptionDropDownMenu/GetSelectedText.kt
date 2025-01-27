package com.example.echojournal.ui.components.MutliOptionDropDownMenu

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