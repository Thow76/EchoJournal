package com.example.echojournal.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.echojournal.ui.theme.MaterialColors
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.echojournal.ui.createentryscreen.TopicViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopicSearchAndCreate(
    viewModel: TopicViewModel = hiltViewModel() // or however you provide ViewModel
) {
    // Observe flows from the ViewModel
    val searchQuery by viewModel.searchQuery.collectAsState()
    val filteredTopics by viewModel.filteredTopics.collectAsState()
    val showSuggestions by viewModel.showSuggestions.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
    ) {
        // The TextField
        TextField(
            value = searchQuery,
            onValueChange = { newValue ->
                // Let the ViewModel handle this
                viewModel.onSearchTextChange(newValue)
            },
            leadingIcon = {
                Icon(
                    modifier = Modifier.size(18.dp),
                    imageVector = Icons.Default.Tag,
                    contentDescription = null,
                    tint = MaterialColors.OutlineVariantNeutralVariant80
                )
            },
            placeholder = { Text("Topic", color = MaterialColors.OutlineVariantNeutralVariant80) },
            // Remove the bottom underline if desired
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                cursorColor = MaterialColors.OutlineVariantNeutralVariant80,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = MaterialTheme.colorScheme.onSurface
            ),
            modifier = Modifier.fillMaxWidth()
        )

        // Show results only if showSuggestions is true
        if (showSuggestions) {
            Card(shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 0.dp, start = 8.dp, end = 8.dp)) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    // Show each matching topic
                    items(filteredTopics) { topic ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.onTopicSelected(topic)
                                }
                                .padding(12.dp)

                        ) {
                            Text(text = topic)
                        }
                    }

                    // If the typed text isn’t in the list, show "Create" option
                    val exactMatchExists = filteredTopics.any {
                        it.equals(searchQuery, ignoreCase = true)
                    }
                    if (searchQuery.isNotEmpty() && !exactMatchExists) {
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        // Let the ViewModel handle creation
                                        viewModel.onCreateTopic()
                                    }
                                    .padding(12.dp)
                            ) {
                                Text("+ Create \'$searchQuery\'")
                            }

                        }
                    }
                }
            }
        }
    }
}







