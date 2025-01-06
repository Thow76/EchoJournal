package com.example.echojournal.ui.components.MutliOptionDropDownMenu

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun MultiSelectDropdownMenu(
    label: String,
    options: List<String>,
    selectedOptions: Set<String>,
    onOptionSelected: (String) -> Unit,
    onOptionDeselected: (String) -> Unit,
    onClearSelection: () -> Unit,
    dropdownWidth: Dp = 380.dp // Default width for the dropdown
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    var toggleAfterReset by rememberSaveable { mutableStateOf(false) } // Tracks if dropdown should reopen after a reset

    val selectedOptionsSorted = selectedOptions.sorted()
    val selectedCategory = if (label == "Moods") selectedOptions else selectedOptionsSorted
    val categorySort = if (selectedCategory == selectedOptions) {
        selectedCategory.joinToString(limit = 2, truncated = "")
    } else {
        selectedCategory.joinToString(limit = 2, truncated = " +${selectedOptions.size - 2}")
    }

    // Update selectedText to "All $label" if all options are selected
    val selectedText = if (selectedOptions.size == options.size) {
        "All $label"
    } else {
        categorySort.ifEmpty { "All $label" }
    }

    // Handle reopening dropdown after a reset
    LaunchedEffect(toggleAfterReset) {
        if (toggleAfterReset) {
            expanded = true
            toggleAfterReset = false
        }
    }

    Box(
        modifier = Modifier
            .padding(2.dp)
    ) {
        // Dynamic Chip replaces the button
        FilterChip(
            colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = Color.White
            ),
            selected = expanded,
            onClick = {
                if (selectedOptions.size == options.size) {
                    onClearSelection() // Deselect all options if all are selected
                    expanded = false // Close dropdown
                    toggleAfterReset = true // Schedule reopening of dropdown
                } else {
                    expanded = !expanded // Toggle dropdown normally
                }
            },
            label = {
                Text(
                    text = selectedText,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            shape = RoundedCornerShape(50),
            border = BorderStroke(
                width = 1.dp,
                color = if (expanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant
            ),
            leadingIcon = {
                // Only show mood icons if not all options are selected
                if (label == "Moods" && selectedOptions.isNotEmpty() && selectedOptions.size < options.size) {
                    MoodsLeadingIcons(selectedOptions = selectedOptions.toList())
                }
            },
            trailingIcon = {
                // Only show the close icon if not all options are selected
                if (selectedOptions.isNotEmpty() && selectedOptions.size < options.size) {
                    ClearSelectionIconButton(onClearSelection = onClearSelection)
                }}
        )

        // Dropdown menu
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(dropdownWidth)
                .background(MaterialTheme.colorScheme.surface)
        ) {
            options.forEach { option ->
                val isSelected = option in selectedCategory
                MultiSelectDropdownMenuItem(
                    option = option,
                    isSelected = isSelected,
                    onOptionSelected = { chosen ->
                        onOptionSelected(chosen)
                        // Close dropdown if all options are selected
                        if (selectedOptions.size + 1 == options.size) {
                            expanded = false
                        }
                    },
                    onOptionDeselected = { chosen ->
                        onOptionDeselected(chosen)
                    }
                )
            }
        }
    }
}










