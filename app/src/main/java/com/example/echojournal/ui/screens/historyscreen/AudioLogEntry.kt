package com.example.echojournal.ui.screens.historyscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.echojournal.R
import com.example.echojournal.data.JournalEntry
import com.example.echojournal.ui.theme.MoodColors

@Composable
fun AudioLogEntry(
    entry: JournalEntry,
    viewModel: JournalHistoryViewModel = viewModel()
) {

    // Determine the mood color
    val entryColour = when (entry.mood) {
        "Neutral" -> MoodColors.Neutral35
        "Stressed" -> MoodColors.Stressed35
        "Sad" -> MoodColors.Sad35
        "Peaceful" -> MoodColors.Peaceful35
        "Excited" -> MoodColors.Excited35
        else -> MaterialTheme.colorScheme.onSurfaceVariant
    }

    // Track whether the full description is shown
    val isExpanded = viewModel.isExpanded(entry.id)

    // Split the description into words
    val lines: List<String> = entry.description.split("\n")

    // Build a short description of only the first 3 words + ellipsis
    val shortDescription = if (lines.size > 3) {
        lines.take(3).joinToString("\n") + "..."
    } else {
        entry.description
    }

    // A Card that wraps the journal entry
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { /* Handle click/navigation if needed */ },
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        // Main layout
        Column(modifier = Modifier.padding(16.dp)) {
            // Row for icon + title on the left, timestamp on the right
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left section: Icon + Title
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = entry.iconResId ?: R.drawable.ic_launcher_foreground),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = entry.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Right section: Timestamp
                Text(
                    text = entry.timeStamp ?: "",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Audio bar (Mood color background)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(entryColour),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Audio Bar Placeholder",
                    style = MaterialTheme.typography.labelMedium
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Description (short or full)
            Text(
                text = if (isExpanded) entry.description else shortDescription,
                style = MaterialTheme.typography.bodyMedium
            )

            // If there's more than 3 words, show a toggle
            if (lines.size > 3) {
                Spacer(modifier = Modifier.height(4.dp))
                TextButton(
                    onClick = { viewModel.toggleExpanded(entry.id)}
                ) {
                    Text(
                        text = if (isExpanded) "Show Less" else "Show More",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}