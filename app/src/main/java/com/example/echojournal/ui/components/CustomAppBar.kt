package com.example.echojournal.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.echojournal.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomAppBar(
    title: String,
    onNavigationClick: (() -> Unit)? = null, // Make this nullable
    icon: @Composable (() -> Unit)? = null // Optional icon parameter
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center,
            )
        },
        navigationIcon = {
            onNavigationClick?.let {
                IconButton(onClick = it) {
                    icon?.invoke() ?: Icon(
                        imageVector = Icons.Default.ChevronLeft, // Default back arrow icon
                        contentDescription = stringResource(R.string.content_description_back), // Accessibility description
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        )
    )
}
