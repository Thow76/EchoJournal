package com.example.echojournal.ui.components.MutliOptionDropDownMenu

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

@Composable
fun MultiSelectDropdownMenuItems(
    options: List<String>,
    selectedCategory: Set<String>,
    onOptionSelected: (String) -> Unit,
    onOptionDeselected: (String) -> Unit,
    label: String,
    onClearSelection: () -> Unit,
    expanded: MutableState<Boolean>,
    toggleAfterReset: MutableState<Boolean>
) {
    options.forEach { option ->
        val isSelected = option in selectedCategory
        MultiSelectDropdownMenuItem(
            option = option,
            isSelected = isSelected,
            onOptionSelected = { chosen ->
                onOptionSelected(chosen)
                if (selectedCategory.size + 1 == options.size) {
                    onClearSelection()
                    expanded.value = false
                    toggleAfterReset.value = true
                }
            },
            onOptionDeselected = { onOptionDeselected(it) },
            label = label
        )
    }
}
