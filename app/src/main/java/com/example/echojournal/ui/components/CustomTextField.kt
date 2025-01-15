package com.example.echojournal.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import com.example.echojournal.ui.theme.MaterialColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    modifier: Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholderText: String = "Add Title...",
    textStyle: TextStyle = MaterialTheme.typography.headlineLarge.copy(
        textAlign = TextAlign.Start
    ),
    leadingIcon: @Composable (() -> Unit)?,
    placeholderStyle: TextStyle = MaterialTheme.typography.headlineLarge,
    placeholderColor: Color = MaterialColors.OutlineVariantNeutralVariant80,
    containerColor: Color = Color.Transparent,
    cursorColor: Color = MaterialTheme.colorScheme.primary,
    focusedIndicatorColor: Color = Color.Transparent,
    unfocusedIndicatorColor: Color = Color.Transparent
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .focusRequester(focusRequester)
            .clickable {
                focusRequester.requestFocus()
                keyboardController?.show()}
            .fillMaxWidth()
            .background(containerColor),
        textStyle = textStyle,
        placeholder = {
            Text(
                text = placeholderText,
                style = placeholderStyle,
                color = placeholderColor
            )
        },
        leadingIcon = leadingIcon, // Can be customized if needed
        colors = TextFieldDefaults.textFieldColors(
            containerColor = containerColor,
            cursorColor = cursorColor,
            focusedIndicatorColor = focusedIndicatorColor,
            unfocusedIndicatorColor = unfocusedIndicatorColor
        )
    )
}
