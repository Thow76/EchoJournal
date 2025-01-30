package com.example.echojournal.ui.screens.createentryscreen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.echojournal.R
import com.example.echojournal.ui.components.CustomTextField
import com.example.echojournal.ui.theme.MaterialColors

@Composable
fun TitleField(
    value: String,
    onValueChange: (String) -> Unit
) {
    CustomTextField(
        value = value,
        onValueChange = onValueChange,
        placeholderText = stringResource(R.string.placeholder_add_title),
        modifier = Modifier.padding(0.dp),
        textStyle = MaterialTheme.typography.headlineLarge.copy(textAlign = TextAlign.Start),
        leadingIcon = null,
        placeholderColor = MaterialColors.OutlineVariantNeutralVariant80,
        containerColor = Color.Transparent
    )
}