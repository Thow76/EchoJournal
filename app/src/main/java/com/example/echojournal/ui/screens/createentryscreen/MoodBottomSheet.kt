package com.example.echojournal.ui.screens.createentryscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.echojournal.ui.components.CustomButton
import com.example.echojournal.ui.components.MutliOptionDropDownMenu.getIcon
//import com.example.echojournal.ui.components.MutliOptionDropDownMenu.getMoodIcon
import com.example.echojournal.ui.components.MutliOptionDropDownMenu.getMoodIconOutline
import com.example.echojournal.ui.theme.Gradients
import com.example.echojournal.ui.theme.Palettes

@Composable
fun MoodBottomSheet(
    moodOptions: Set<String>,
    onConfirm: (String) -> Unit,
    onCancel: () -> Unit
) {
    var selectedMood by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "How are you doing?",
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(32.dp))

        // Mood icons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            moodOptions.forEach { mood ->
                val isSelected = (mood == selectedMood)
                val icon = if (isSelected) getIcon(label = "Moods", option = mood) else getMoodIconOutline(mood)

                Column(
                    modifier = Modifier
                        .clickable {
                            // Toggle selection
                            selectedMood = if (isSelected) null else mood
                        }
                        .padding(horizontal = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (icon != null) {
                        Icon(
                            imageVector = icon,
                            contentDescription = mood,
                            modifier = Modifier.size(48.dp),
                            tint = if (isSelected) Color.Unspecified else Palettes.Secondary70
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = mood,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }

        // Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Bottom
        ) {
            CustomButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(
                        brush = Gradients.BgSaturateGradient,
                        shape = RoundedCornerShape(50.dp)
                    ),
                onClick = {
                    // Cancel action -> use parent callback
                    onCancel()
                },
                text = "Cancel",
                shape = RoundedCornerShape(50.dp),
                backgroundColor = Color.Transparent,
                textColor = MaterialTheme.colorScheme.primary,
                enabled = null
            )

            Spacer(modifier = Modifier.width(16.dp))

            CustomButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f)
                    .background(
                        brush = if (selectedMood != null) {
                            Gradients.ButtonGradient
                        } else {
                            Gradients.ButtonRequiredGradient
                        },
                        shape = RoundedCornerShape(50.dp)
                    ),
                onClick = {
                    // Only confirm if a mood is selected
                    if (selectedMood != null) {
                        onConfirm(selectedMood!!)
                    } else {
                        // Possibly show a warning or do nothing
                    }
                },
                text = "Confirm",
                textColor = if (selectedMood != null) {
                    MaterialTheme.colorScheme.onPrimary
                } else {
                    MaterialTheme.colorScheme.outline
                },
                enabled = null,
                shape = RoundedCornerShape(50.dp),
                backgroundColor = Color.Transparent,
                icon = Icons.Default.Check,
                iconTint = if (selectedMood != null) {
                    Color.White
                } else {
                    MaterialTheme.colorScheme.outline
                }
            )
        }
    }
}



