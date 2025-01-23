package com.example.echojournal.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogProperties

@Composable
fun CustomAlertDialog(
    modifier: Modifier,
    title: String = "",
    message: String = "",
    cancelButtonText: String = "Confirm",
    confirmButtonText: String = "Cancel",
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
    properties: DialogProperties = DialogProperties(),
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = { /* Optional: handle dialog dismissal */ },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
        title = {
            Text(text = title)
        },
        text = {
            Text(text = message)
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = confirmButtonText,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.secondary)
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text(
                    text = cancelButtonText,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    )
}
