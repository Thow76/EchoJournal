package com.example.echojournal.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableChipColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.echojournal.R
import com.example.echojournal.ui.theme.EchoJournalTheme
import com.example.echojournal.ui.theme.Palettes


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
    var expanded by remember { mutableStateOf(false) }
    var toggleAfterReset by remember { mutableStateOf(false) } // Tracks if dropdown should reopen after a reset

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
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        selectedOptions.take(2).forEach { option ->
                            getIconForOption(option)?.let { icon ->
                                Icon(
                                    imageVector = icon,
                                    tint = Color.Unspecified,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }
            },
            trailingIcon = {
                // Only show the close icon if not all options are selected
                if (selectedOptions.isNotEmpty() && selectedOptions.size < options.size) {
                    IconButton(
                        modifier = Modifier.size(16.dp),
                        onClick = { onClearSelection() }
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Clear Selection")
                    }
                }
            },
            modifier = Modifier
                .widthIn(min = 100.dp, max = 250.dp)
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
                DropdownMenuItem(
                    onClick = {
                        if (isSelected) {
                            onOptionDeselected(option) // Deselect option
                        } else {
                            onOptionSelected(option) // Select option
                        }
                        // Close dropdown if all options are selected
                        if (selectedOptions.size + 1 == options.size) {
                            expanded = false
                        }
                    },
                    text = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(if (isSelected) Palettes.Secondary95 else Color.Transparent)
                                .padding(8.dp)
                        ) {
                            // Add icon with Color.Unspecified tint to preserve original color
                            getIconForOption(option)?.let { icon ->
                                Icon(
                                    imageVector = icon,
                                    contentDescription = null,
                                    tint = Color.Unspecified // Retain original vector color
                                )
                            }
                            Text(
                                text = option,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.weight(1f)
                            )
                            if (isSelected) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Selected",
                                    tint = MaterialTheme.colorScheme.outline
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun getIconForOption(option: String): ImageVector? {
    return when (option) {
        "Stressed" -> ImageVector.vectorResource(R.drawable.stressed_mood)
        "Sad" -> ImageVector.vectorResource(R.drawable.sad_mood)
        "Neutral" -> ImageVector.vectorResource(R.drawable.neutral_mood)
        "Peaceful" -> ImageVector.vectorResource(R.drawable.peaceful_mood)
        "Excited" -> ImageVector.vectorResource(R.drawable.excited_mood)
        else -> ImageVector.vectorResource(R.drawable.hash_icon)
    }
}












