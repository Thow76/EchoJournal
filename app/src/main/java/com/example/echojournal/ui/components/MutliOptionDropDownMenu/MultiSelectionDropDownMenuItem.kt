package com.example.echojournal.ui.components.MutliOptionDropDownMenu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.echojournal.ui.theme.Palettes

@Composable
fun MultiSelectDropdownMenuItem(
    label: String,
    option: String,
    isSelected: Boolean,
    onOptionSelected: (String) -> Unit,
    onOptionDeselected: (String) -> Unit
) {
    DropdownMenuItem(
        onClick = {
            if (isSelected) {
                onOptionDeselected(option)
            } else {
                onOptionSelected(option)
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
                val icon = getIcon(label, option)
                icon?.let {
                    Icon(imageVector = it, contentDescription = null, tint = Color.Unspecified)
                    Spacer(modifier = Modifier.width(4.dp))
                }
                // Item text
                Text(
                    text = option,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
                // Checkmark if selected
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


