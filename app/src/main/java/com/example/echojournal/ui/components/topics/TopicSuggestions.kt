package com.example.echojournal.ui.components.topics

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.echojournal.R

@Composable
fun TopicSuggestionsDropdown(
    topics: List<String>,
    query: String,
    onTopicSelected: (String) -> Unit,
    onCreateTopic: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(topics) { topic ->
                TopicSuggestionItem(
                    text = topic,
                    onClick = { onTopicSelected(topic) }
                )
            }

            // Show "Create" option if no exact match exists
            val exactMatchExists = topics.any { it.equals(query, ignoreCase = true) }
            if (query.isNotBlank() && !exactMatchExists) {
                item {
                    TopicSuggestionCreateItem(
                        query = query,
                        onCreate = onCreateTopic
                    )
                }
            }
        }
    }
}

@Composable
fun TopicSuggestionItem(
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(12.dp)
    ) {
        Text(text = text, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun TopicSuggestionCreateItem(
    query: String,
    onCreate: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onCreate)
            .padding(12.dp)
    ) {
        Text(text = stringResource(R.string.topic_create, query), style = MaterialTheme.typography.bodyMedium)
    }
}