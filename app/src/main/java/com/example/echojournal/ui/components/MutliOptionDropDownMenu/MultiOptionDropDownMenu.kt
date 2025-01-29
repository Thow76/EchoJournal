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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.echojournal.ui.components.utils.getSelectedText
import com.example.echojournal.ui.components.utils.handleDropdownToggle

@Composable
fun MultiSelectDropdownMenu(
    label: String,
    options: List<String>,
    selectedOptions: Set<String>,
    onOptionSelected: (String) -> Unit,
    onOptionDeselected: (String) -> Unit,
    onClearSelection: () -> Unit,
    dropdownWidth: Dp = 380.dp
) {
    // Declare states
    val expanded = rememberSaveable { mutableStateOf(false) }
    val toggleAfterReset = rememberSaveable { mutableStateOf(false) }

    val isAllSelected = selectedOptions.size == options.size
    val selectedText = getSelectedText(label, selectedOptions, options)

    // Handle reopening dropdown after reset
    LaunchedEffect(toggleAfterReset.value) {
        if (toggleAfterReset.value) {
            expanded.value = true
            toggleAfterReset.value = false
        }
    }

    Box(modifier = Modifier.padding(2.dp)) {
        FilterChip(
            colors = FilterChipDefaults.filterChipColors(
                containerColor = if (selectedOptions.isEmpty()) {
                    Color.Transparent
                } else {
                    Color.White
                }

            ),
            selected = expanded.value, // Access value for Boolean
            onClick = {
                handleDropdownToggle(
                    isAllSelected = isAllSelected,
                    onClearSelection = onClearSelection,
                    expanded = expanded, // Pass MutableState
                    toggleAfterReset = toggleAfterReset // Pass MutableState
                )
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
                color = if (expanded.value || selectedOptions.isNotEmpty()) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.outlineVariant
                }
            ),
            leadingIcon = {
                if (selectedOptions.isNotEmpty() && selectedOptions.size < options.size) {
                    LeadingIcons(label, selectedOptions)
                }
            },
            trailingIcon = {
                if (selectedOptions.isNotEmpty() && selectedOptions.size < options.size) {
                    ClearSelectionIconButton(onClearSelection = onClearSelection)
                }
            }
        )

        DropdownMenu(
            expanded = expanded.value, // Access value for Boolean
            onDismissRequest = { expanded.value = false }, // Update value
            modifier = Modifier
                .width(dropdownWidth)
                .background(MaterialTheme.colorScheme.surface)
        ) {
            MultiSelectDropdownMenuItems(
                options = options,
                selectedCategory = selectedOptions,
                onOptionSelected = onOptionSelected,
                onOptionDeselected = onOptionDeselected,
                label = label,
                onClearSelection = onClearSelection,
                expanded = expanded, // Pass MutableState
                toggleAfterReset = toggleAfterReset // Pass MutableState
            )
        }
    }
}











