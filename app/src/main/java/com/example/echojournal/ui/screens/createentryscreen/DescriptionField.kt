package com.example.echojournal.ui.screens.createentryscreen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.echojournal.ui.components.CustomTextField
import com.example.echojournal.ui.theme.MaterialColors

@Composable
fun DescriptionField(
    value: String,
    onValueChange: (String) -> Unit
) {
    CustomTextField(
        value = value,
        onValueChange = onValueChange,
        placeholderText = "Add Description...",
        modifier = Modifier.padding(start = 8.dp),
        textStyle = MaterialTheme.typography.bodyLarge.copy(textAlign = TextAlign.Start),
        leadingIcon = {
            Icon(
                modifier = Modifier.size(18.dp),
                imageVector = Icons.Default.Edit,
                contentDescription = "Add Description",
                tint = MaterialColors.OutlineVariantNeutralVariant80
            )
        },
        placeholderColor = MaterialColors.OutlineVariantNeutralVariant80,
        placeholderStyle = MaterialTheme.typography.bodyLarge,
        containerColor = Color.Transparent
    )
}