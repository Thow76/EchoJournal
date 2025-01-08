package com.example.echojournal.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import com.example.echojournal.ui.theme.Gradients

@Composable
fun CustomGradientButton(
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    contentDescription: String
) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .size(64.dp) // Standard FAB size
            // Provide semantics to the entire FAB:
            .semantics {
                this.contentDescription = contentDescription
                this.role = androidx.compose.ui.semantics.Role.Button
            }
            .background(
                brush = Gradients.ButtonGradient,
                shape = CircleShape
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        icon()
    }
}
