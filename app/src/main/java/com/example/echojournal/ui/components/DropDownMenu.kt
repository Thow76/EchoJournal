package com.example.echojournal.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
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
    val selectedText = if (selectedOptions.isEmpty()) "All $label" else selectedOptions.joinToString(", ")

    Box(
        modifier = Modifier
            .padding(8.dp)
    ) {
        // Button that triggers the dropdown
        Button(
            onClick = { expanded = !expanded },
            modifier = Modifier,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            ),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
        ) {
            Row {
                Text(
                    text = selectedText,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,

                    )
                // Cancellation cross icon
                if (selectedOptions.isNotEmpty()) {
                    IconButton(
                        onClick = {
                            onClearSelection() // Clear all selections
                        },
                        modifier = Modifier.size(20.dp) // Consistent icon size
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close, // Use a close or cancel icon
                            contentDescription = "Clear Selection",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
        // Dropdown menu
        androidx.compose.material3.DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(dropdownWidth)
                .background(MaterialTheme.colorScheme.surface)
        ) {
            options.forEach { option ->
                val isSelected = option in selectedOptions
                androidx.compose.material3.DropdownMenuItem(
                    onClick = {
                        if (isSelected) {
                            onOptionDeselected(option) // Deselect option
                        } else {
                            onOptionSelected(option) // Select option
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
            }}}}







