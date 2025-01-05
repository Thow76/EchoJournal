package com.example.echojournal.ui.components.MutliOptionDropDownMenu

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ClearSelectionIconButton(onClearSelection: () -> Unit) {
    IconButton(
        modifier = Modifier.size(16.dp),
        onClick = { onClearSelection() }
    ) {
        Icon(Icons.Default.Close, contentDescription = "Clear Selection")
    }
}
