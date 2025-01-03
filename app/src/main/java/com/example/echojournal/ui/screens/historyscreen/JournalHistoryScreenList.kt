package com.example.echojournal.ui.screens.historyscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.echojournal.data.JournalEntry
import com.example.echojournal.ui.theme.Gradients


@Composable
fun JournalHistoryScreenList(journalEntries: List<JournalEntry>) {
    Box (modifier = Modifier
        .fillMaxSize()
        .background(brush = Gradients.BgSaturateGradient)){
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(journalEntries) { entry ->
                AudioLogEntry(entry = entry)
            }
        }
    }
}


