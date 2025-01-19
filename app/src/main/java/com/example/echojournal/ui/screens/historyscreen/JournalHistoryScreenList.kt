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
import com.example.echojournal.model.JournalEntry
import com.example.echojournal.ui.theme.Gradients
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun JournalHistoryScreenList(journalEntries: List<JournalEntry>) {
    // Define the date format matching the format of `it.date`
    val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())

    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DATE, -1) // Subtract one day
    val yesterday = dateFormat.format(calendar.time)

// Get the formatted current date as a string
    val today = dateFormat.format(Date())
    val todayEntries = journalEntries.filter { it.date == today }
    val yesterdayEntries = journalEntries.filter { it.date == yesterday }
    // We’ll group the other entries by their date
    val otherEntries = journalEntries.filterNot { it.date == today || it.date == yesterday }
    val groupedOtherEntries = otherEntries.groupBy { it.date }

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
                items(todayEntries, key = { it.id }) { entry ->
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
                items(yesterdayEntries, key = { it.id }) { entry ->
                    AudioLogEntry(entry = entry)
                }
            }

            // Group other entries by date and list each group under a heading
            groupedOtherEntries.forEach { (date, entriesForDate) ->
                item {
                    Text(
                        text = date,
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
                items(entriesForDate, key = { it.id }) { entry ->
                    AudioLogEntry(entry = entry)
                }
            }
        }
    }
}





