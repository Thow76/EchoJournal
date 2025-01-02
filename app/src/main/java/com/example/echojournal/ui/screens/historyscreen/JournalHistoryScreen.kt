package com.example.echojournal.ui.screens.historyscreen

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun JournalHistoryScreen(
    navController: NavController) {
    val dummyEntries = listOf(
        "Audio Log 1: Morning Reflection",
        "Audio Log 2: Afternoon Thoughts",
        "Audio Log 3: Evening Recap"
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(dummyEntries) { entry ->
            AudioLogEntry(entry = entry)
        }
    }
}

@Composable
fun AudioLogEntry(entry: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = entry,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewJournalHistoryScreen() {
    JournalHistoryScreen(rememberNavController())
}
