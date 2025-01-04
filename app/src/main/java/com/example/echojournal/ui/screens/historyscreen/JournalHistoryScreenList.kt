package com.example.echojournal.ui.screens.historyscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.echojournal.data.JournalEntry
import com.example.echojournal.ui.theme.Gradients


@Composable
fun JournalHistoryScreenList(journalEntries: List<JournalEntry>) {
    val todayEntries = journalEntries.filter { it.date == "Today" }
    val yesterdayEntries = journalEntries.filter { it.date == "Yesterday" }
    val otherEntries = journalEntries.filterNot { it.date == "Today" || it.date == "Yesterday" }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Gradients.BgSaturateGradient)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Today's entries
            if (todayEntries.isNotEmpty()) {
                item {
                    Text(
                        text = "Today",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
                items(todayEntries) { entry ->
                    AudioLogEntry(entry = entry)
                }
            }

            // Yesterday's entries
            if (yesterdayEntries.isNotEmpty()) {
                item {
                    Text(
                        text = "Yesterday",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
                items(yesterdayEntries) { entry ->
                    AudioLogEntry(entry = entry)
                }
            }

            // Other entries
            if (otherEntries.isNotEmpty()) {
                items(otherEntries) { entry ->
                    AudioLogEntry(entry = entry)
                }
            }
        }
    }
}

