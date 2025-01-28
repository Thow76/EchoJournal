package com.example.echojournal.ui.screens.createentryscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.echojournal.ui.components.topics.TopicSearchTextField

@Composable
fun MultiTopicSelectionRow(
    query: String,
    onQueryChange: (String) -> Unit,
    selectedTopics: List<String>,
    onTopicSelectedOrCreated: (String) -> Unit,
    onTopicRemove: (String) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        // 1) The TopicSearchTextField (weighted 2:1, adjust as you like)
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)

        ) {
            TopicSearchTextField(
                query = query,
                onQueryChange = onQueryChange
            )
        }
    }
}