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

//@Composable
//fun JournalHistoryScreenList(journalEntries: List<JournalEntry>) {
//    // Define the date format matching the format of `it.date`
//    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
//    // Define the date format for "Saturday, Dec 28"
//    val dayFormat = SimpleDateFormat("EEEE, MMM dd", Locale.getDefault())
//
//    val calendar = Calendar.getInstance()
//    calendar.add(Calendar.DATE, -1) // Subtract one day
//    val yesterday = dateFormat.format(calendar.time)
//    val abbreviateDate = dayFormat.format(calendar.time)
//
//// Get the formatted current date as a string
//    val today = dateFormat.format(Date())
//    val todayEntries = journalEntries.filter { it.date == today }
//    val yesterdayEntries = journalEntries.filter { it.date == yesterday }
//    // We’ll group the other entries by their date
//    val otherEntries = journalEntries.filterNot { it.date == today || it.date == yesterday }
//    val groupedOtherEntries = otherEntries.groupBy { it.date }
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(brush = Gradients.BgSaturateGradient)
//    ) {
//        LazyColumn(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp),
//            verticalArrangement = Arrangement.spacedBy(8.dp)
//        ) {
//            // Today's entries
//            if (todayEntries.isNotEmpty()) {
//                item {
//                    Text(
//                        text = "Today",
//                        style = MaterialTheme.typography.headlineSmall,
//                        color = MaterialTheme.colorScheme.onSurface,
//                        modifier = Modifier.padding(bottom = 8.dp)
//                    )
//                }
//                items(todayEntries, key = { it.id }) { entry ->
//                    AudioLogEntry(entry = entry)
//                }
//            }
//
//            // Yesterday's entries
//            if (yesterdayEntries.isNotEmpty()) {
//                item {
//                    Text(
//                        text = "Yesterday",
//                        style = MaterialTheme.typography.headlineSmall,
//                        color = MaterialTheme.colorScheme.onSurface,
//                        modifier = Modifier.padding(vertical = 8.dp)
//                    )
//                }
//                items(yesterdayEntries, key = { it.id }) { entry ->
//                    AudioLogEntry(entry = entry)
//                }
//            }
//
//            // Group other entries by date and list each group under a heading
//            // Group other entries by date and display them in the new format
//            groupedOtherEntries.forEach { (date, entriesForDate) ->
//                val formattedDate = try {
//                    abbreviateDate.format(dateFormat.parse(date)!!) // Format the date into "Saturday, Dec 28"
//                } catch (e: Exception) {
//                    date // Fallback to the original date string if parsing fails
//                }
//                item {
//                    Text(
//                        text = formattedDate,
//                        style = MaterialTheme.typography.headlineSmall,
//                        color = MaterialTheme.colorScheme.onSurface,
//                        modifier = Modifier.padding(vertical = 8.dp)
//                    )
//                }
//                items(entriesForDate, key = { it.id }) { entry ->
//                    AudioLogEntry(entry = entry)
//                }
//            }
//        }
//    }
//}

@Composable
fun JournalHistoryScreenList(journalEntries: List<JournalEntry>) {
    // Define the date format matching the format of `it.date`
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    // Define the date format for "Saturday, Dec 28"
    val dayFormat = SimpleDateFormat("EEEE, MMM dd", Locale.getDefault())

    // Calculate yesterday's date
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DATE, -1) // Subtract one day
    val yesterday = dateFormat.format(calendar.time)

    // Get today's date
    val today = dateFormat.format(Date())

    // Filter and group journal entries
    val todayEntries = journalEntries.filter { it.date == today }.sortedByDescending { it.timeStamp }
    val yesterdayEntries = journalEntries.filter { it.date == yesterday }.sortedByDescending { it.timeStamp }
    val otherEntries = journalEntries.filterNot { it.date == today || it.date == yesterday }.sortedByDescending { it.timeStamp }
    val groupedOtherEntries = otherEntries.sortedByDescending { it.timeStamp }.groupBy { it.date }

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
                val formattedDate = try {
                    // Create a new calendar for parsing and formatting this specific date
                    val parsedDate = dateFormat.parse(date)
                    if (parsedDate != null) dayFormat.format(parsedDate) else date
                } catch (e: Exception) {
                    date // Fallback to the original date string if parsing fails
                }
                item {
                    Text(
                        text = formattedDate,
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


//@Composable
//fun JournalHistoryScreenList(journalEntries: List<JournalEntry>) {
//    // Define the date format for grouping comparison
//    val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
//    // Define the new date format for displaying groupedOtherEntries
//    val displayFormat = SimpleDateFormat("EEEE, MMM dd", Locale.getDefault())
//
//    // Get "Yesterday" and "Today" for filtering
//    val calendar = Calendar.getInstance()
//    calendar.add(Calendar.DATE, -1) // Subtract one day for "Yesterday"
//    val yesterday = dateFormat.format(calendar.time)
//    val today = dateFormat.format(Date())
//
//    // Filter entries based on date
//    val todayEntries = journalEntries.filter { it.date == today }
//    val yesterdayEntries = journalEntries.filter { it.date == yesterday }
//    val otherEntries = journalEntries.filterNot { it.date == today || it.date == yesterday }
//    val groupedOtherEntries = otherEntries.groupBy { it.date }
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(brush = Gradients.BgSaturateGradient)
//    ) {
//        LazyColumn(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp),
//            verticalArrangement = Arrangement.spacedBy(8.dp)
//        ) {
//            // Today's entries
//            if (todayEntries.isNotEmpty()) {
//                item {
//                    Text(
//                        text = "Today",
//                        style = MaterialTheme.typography.headlineSmall,
//                        color = MaterialTheme.colorScheme.onSurface,
//                        modifier = Modifier.padding(bottom = 8.dp)
//                    )
//                }
//                items(todayEntries, key = { it.id }) { entry ->
//                    AudioLogEntry(entry = entry)
//                }
//            }
//
//            // Yesterday's entries
//            if (yesterdayEntries.isNotEmpty()) {
//                item {
//                    Text(
//                        text = "Yesterday",
//                        style = MaterialTheme.typography.headlineSmall,
//                        color = MaterialTheme.colorScheme.onSurface,
//                        modifier = Modifier.padding(vertical = 8.dp)
//                    )
//                }
//                items(yesterdayEntries, key = { it.id }) { entry ->
//                    AudioLogEntry(entry = entry)
//                }
//            }
//
//            // Group other entries by date and display them in the new format
//            groupedOtherEntries.forEach { (date, entriesForDate) ->
//                val formattedDate = try {
//                    displayFormat.format(dateFormat.parse(date)!!) // Format the date into "Saturday, Dec 28"
//                } catch (e: Exception) {
//                    date // Fallback to the original date string if parsing fails
//                }
//
//                item {
//                    Text(
//                        text = formattedDate,
//                        style = MaterialTheme.typography.headlineSmall,
//                        color = MaterialTheme.colorScheme.onSurface,
//                        modifier = Modifier.padding(vertical = 8.dp)
//                    )
//                }
//                items(entriesForDate, key = { it.id }) { entry ->
//                    AudioLogEntry(entry = entry)
//                }
//            }
//        }
//    }
//}





