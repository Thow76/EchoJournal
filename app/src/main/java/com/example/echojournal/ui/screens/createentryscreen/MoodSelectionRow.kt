package com.example.echojournal.ui.screens.createentryscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.echojournal.R
import com.example.echojournal.ui.components.CustomGradientIconButton
import com.example.echojournal.ui.components.utils.getIcon
import com.example.echojournal.ui.theme.Gradients
import com.example.echojournal.ui.theme.Palettes

@Composable
fun MoodSelectionRow(
    selectedMood: String?,
    onOpenMoodSheet: () -> Unit
) {
    Box()
        {
        if (selectedMood == null) {
            CustomGradientIconButton(
                modifier = Modifier.size(32.dp),
                onClick = onOpenMoodSheet,
                icon = {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(R.string.content_description_choose_mood),
                        tint = Palettes.Secondary70
                    )
                },
                contentDescription = "Choose Mood",
                buttonGradient = Gradients.BgSaturateGradient
            )
        } else {
            getIcon(label = "Moods", option = selectedMood)?.let {
                Icon(
                    modifier = Modifier
                        .size(32.dp)
                        .clickable { onOpenMoodSheet() },
                    imageVector = it,
                    contentDescription = stringResource(R.string.content_description_selected_mood),
                    tint = Color.Unspecified
                )
            }
        }
    }
}
