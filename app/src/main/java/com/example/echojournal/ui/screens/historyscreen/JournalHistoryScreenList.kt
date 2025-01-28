package com.example.echojournal.ui.screens.historyscreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.echojournal.model.JournalEntry
import com.example.echojournal.ui.theme.Gradients
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun JournalHistoryScreenList(journalEntries: List<JournalEntry>) {
    // Define the date formats
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val dayFormat = SimpleDateFormat("EEEE, MMM dd", Locale.getDefault())

    // Calculate 'Yesterday' and 'Today' dates
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DATE, -1) // Subtract one day for 'Yesterday'
    val yesterday = dateFormat.format(calendar.time)
    val today = dateFormat.format(Date())

    // Filter and group journal entries
    val todayEntries = journalEntries.filter { it.date == today }.sortedByDescending { it.timeStamp }
    val yesterdayEntries = journalEntries.filter { it.date == yesterday }.sortedByDescending { it.timeStamp }
    val otherEntries = journalEntries.filterNot { it.date == today || it.date == yesterday }
    val groupedOtherEntries = otherEntries.sortedByDescending { it.timeStamp }.groupBy { it.date }

    // Handle grouped entries
    val categorizedEntries = buildList {
        if (todayEntries.isNotEmpty()) {
            add("Today" to todayEntries)
        }
        if (yesterdayEntries.isNotEmpty()) {
            add("Yesterday" to yesterdayEntries)
        }
        groupedOtherEntries.forEach { (date, entries) ->
            val formattedDate = try {
                val parsedDate = dateFormat.parse(date)
                parsedDate?.let { dayFormat.format(it) } ?: date
            } catch (e: Exception) {
                Log.e("JournalHistoryScreenList", "Date parsing error: ${e.message}")
                date
            }
            add(formattedDate to entries)
        }
    }

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
            // Render categorized entries
            categorizedEntries.forEach { (header, entries) ->
                item {
                    Text(
                        text = header,
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
                items(entries, key = { it.id }) { entry ->
                    AudioLogEntry(entry = entry)
                }
            }

            // Empty state
            if (journalEntries.isEmpty()) {
                item {
                    Text(
                        text = "No journal entries available.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                    )
                }
            }
        }
    }
}










