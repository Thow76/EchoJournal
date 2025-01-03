package com.example.echojournal.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun FilterSection(
    selectedMoods: Set<String>,
    selectedTopics: Set<String>,
    onMoodSelected: (String) -> Unit,
    onMoodDeselected: (String) -> Unit,
    onTopicSelected: (String) -> Unit,
    onTopicDeselected: (String) -> Unit,
    onClearMoodSelection: () -> Unit,
    onClearTopicSelection: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        MultiSelectDropdownMenu(
            label = "Moods",
            options = listOf("Stressed", "Sad", "Neutral", "Peaceful", "Excited"),
            selectedOptions = selectedMoods,
            onOptionSelected = onMoodSelected,
            onOptionDeselected = onMoodDeselected,
            onClearSelection = onClearMoodSelection
        )

        Spacer(modifier = Modifier.height(8.dp))

        MultiSelectDropdownMenu(
            label = "Topics",
            options = listOf("Work", "Friends", "Family", "Love", "Surprise"),
            selectedOptions = selectedTopics,
            onOptionSelected = onTopicSelected,
            onOptionDeselected = onTopicDeselected,
            onClearSelection = onClearTopicSelection
        )
    }
}




