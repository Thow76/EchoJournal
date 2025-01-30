package com.example.echojournal.ui.screens.createentryscreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.echojournal.R
import com.example.echojournal.ui.components.CustomButton
import com.example.echojournal.ui.theme.Gradients

@Composable
fun CreateRecordScreenBottomBar(
    isSaveEnabled: Boolean,
    onCancel: () -> Unit,
    onSave: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 54.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Bottom
    ) {
        CustomButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)
                .weight(1f)
                .background(
                    brush = Gradients.BgSaturateGradient,
                    shape = RoundedCornerShape(50.dp)
                ),
            onClick = onCancel,
            text = stringResource(R.string.button_cancel),
            shape = RoundedCornerShape(50.dp),
            backgroundColor = Color.Transparent,
            textColor = MaterialTheme.colorScheme.primary,
            enabled = null
        )

        Spacer(modifier = Modifier.width(8.dp))

        CustomButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp)
                .weight(2f)
                .background(
                    brush = if (isSaveEnabled) {
                        Gradients.ButtonGradient
                    } else {
                        Gradients.ButtonRequiredGradient
                    },
                    shape = RoundedCornerShape(50.dp)
                ),
            onClick = {
                if (!isSaveEnabled) {
                    Log.w("CreateEntryScreen", "Title and Mood are required!")
                    return@CustomButton
                }
                onSave()
            },
            text = stringResource(R.string.button_save),
            enabled = isSaveEnabled,
            textColor = if (isSaveEnabled) {
                MaterialTheme.colorScheme.onPrimary
            } else {
                MaterialTheme.colorScheme.outline
            },
            shape = RoundedCornerShape(50.dp),
            backgroundColor = Color.Transparent,
            iconTint = if (isSaveEnabled) {
                Color.White
            } else {
                MaterialTheme.colorScheme.outline
            }
        )
    }
}
