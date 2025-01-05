package com.example.echojournal.ui.components.MutliOptionDropDownMenu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MoodsLeadingIcons(selectedOptions: List<String>) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        selectedOptions.take(2).forEach { option ->
            getMoodIconForDropDown(option)?.let { icon ->
                Icon(
                    imageVector = icon,
                    tint = Color.Unspecified,
                    contentDescription = null
                )
            }
        }
    }
}


