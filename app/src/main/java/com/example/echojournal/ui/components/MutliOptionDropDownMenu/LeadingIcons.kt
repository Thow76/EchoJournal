package com.example.echojournal.ui.components.MutliOptionDropDownMenu

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.echojournal.ui.components.utils.getIcon

@Composable
fun LeadingIcons(label: String, selectedOptions: Set<String>) {
    Row {
        selectedOptions.take(2).forEach { option ->
            getIcon(label, option)?.let { icon ->
                Icon(imageVector = icon, contentDescription = null, tint = Color.Unspecified)
                Spacer(modifier = Modifier.width(4.dp))
            }
        }
    }
}
