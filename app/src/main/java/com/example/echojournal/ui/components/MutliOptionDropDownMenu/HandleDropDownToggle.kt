package com.example.echojournal.ui.components.MutliOptionDropDownMenu

import androidx.compose.runtime.MutableState

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