package com.example.echojournal.ui.components.topics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TopicsRow(topics: List<String>) {
    Row(
        // Add horizontal spacing, adjust as you wish
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        // Optionally fill the width or wrap content
        modifier = Modifier.wrapContentWidth().padding(top = 8.dp, start = 8.dp)
    ) {
        topics.forEach { topic ->
            TopicChip(
                text = topic,
            )
        }
    }
}