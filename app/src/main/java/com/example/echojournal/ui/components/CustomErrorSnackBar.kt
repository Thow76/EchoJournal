package com.example.echojournal.ui.components

import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ErrorSnackbar(modifier: Modifier = Modifier, errorMessage: String?, onDismiss: () -> Unit) {
    Snackbar(
        action = {
            TextButton(onClick = onDismiss) {
                Text("Dismiss")
            }
        },
        modifier = Modifier
    ) {
        Text(text = errorMessage ?: "An error occurred.")
    }
}