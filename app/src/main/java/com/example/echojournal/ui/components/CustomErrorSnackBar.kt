package com.example.echojournal.ui.components

import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.echojournal.R

@Composable
fun ErrorSnackbar(modifier: Modifier = Modifier, errorMessage: String?, onDismiss: () -> Unit) {
    Snackbar(
        action = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.snackbar_dismiss))
            }
        },
        modifier = Modifier
    ) {
        Text(text = errorMessage ?: stringResource(R.string.snackbar_error_occurred))
    }
}