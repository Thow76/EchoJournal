package com.example.echojournal.ui.screens.createentryscreen

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.echojournal.ui.theme.MaterialColors
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.hilt.navigation.compose.hiltViewModel

//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun TopicSearchAndCreate(
//    viewModel: TopicViewModel = hiltViewModel() // or however you provide ViewModel
//) {
//    // Observe flows from the ViewModel
//    val searchQuery by viewModel.searchQuery.collectAsState()
//    val filteredTopics by viewModel.filteredTopics.collectAsState()
//    val showSuggestions by viewModel.showSuggestions.collectAsState()
//
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(start = 16.dp, end = 16.dp)
//    ) {
//        // The TextField
//        TextField(
//            value = searchQuery,
//            onValueChange = { newValue ->
//                // Let the ViewModel handle this
//                viewModel.onSearchTextChange(newValue)
//            },
//            leadingIcon = {
//                Icon(
//                    modifier = Modifier.size(18.dp),
//                    imageVector = Icons.Default.Tag,
//                    contentDescription = null,
//                    tint = MaterialColors.OutlineVariantNeutralVariant80
//                )
//            },
//            placeholder = { Text("Topic", color = MaterialColors.OutlineVariantNeutralVariant80) },
//            // Remove the bottom underline if desired
//            colors = TextFieldDefaults.textFieldColors(
//                containerColor = Color.Transparent,
//                cursorColor = MaterialColors.OutlineVariantNeutralVariant80,
//                focusedIndicatorColor = Color.Transparent,
//                unfocusedIndicatorColor = Color.Transparent,
//                focusedTextColor = MaterialTheme.colorScheme.onSurface
//            ),
//            modifier = Modifier.fillMaxWidth()
//        )
//        // Show results only if showSuggestions is true
//        if (showSuggestions) {
//            Card(shape = RoundedCornerShape(10.dp),
//                colors = CardDefaults.cardColors(
//                    containerColor = Color.White
//                ),
//                elevation = CardDefaults.cardElevation(4.dp),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(top = 0.dp, start = 8.dp, end = 8.dp)
//                    .onFocusChanged {  }) {
//                LazyColumn(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                ) {
//                    // Show each matching topic
//                    items(filteredTopics) { topic ->
//                        Row(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .clickable {viewModel.onTopicSelected(topic)}
//                                .padding(12.dp)
//                        ) {
//                            Text(text = topic)
//                        }
//                    }
//                    // If the typed text isn’t in the list, show "Create" option
//                    val exactMatchExists = filteredTopics.any {
//                        it.equals(searchQuery, ignoreCase = true)
//                    }
//                    if (searchQuery.isNotEmpty() && !exactMatchExists) {
//                        item {
//                            Row(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .clickable {
//                                        // Let the ViewModel handle creation
//                                        viewModel.onCreateTopic()
//                                    }
//                                    .padding(12.dp)
//                            ) {
//                                Text("+ Create \'$searchQuery\'")
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopicSearchAndCreate(
    viewModel: TopicViewModel = hiltViewModel()
) {
    // Collect the entire UI state from the ViewModel
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        // Search TextField
        TopicSearchTextField(
            query = uiState.searchQuery,
            onQueryChange = viewModel::onSearchTextChange
        )

        // Suggestions Dropdown
        if (uiState.showSuggestions) {
            SuggestionsDropdown(
                topics = uiState.filteredTopics,
                query = uiState.searchQuery,
                onTopicSelected = viewModel::onTopicSelected,
                onCreateTopic = viewModel::onCreateTopic
            )
        }

        // Optional: Handle loading, error, or creation states
        uiState.isCreating?.let { isCreating ->
            if (isCreating) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        }

        uiState.errorMessage?.let { errorMessage ->
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopicSearchTextField(
    query: String,
    onQueryChange: (String) -> Unit
) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Tag,
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                tint = MaterialColors.OutlineVariantNeutralVariant80
            )
        },
        placeholder = {
            Text("Topic", color = MaterialColors.OutlineVariantNeutralVariant80)
        },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
            cursorColor = MaterialColors.OutlineVariantNeutralVariant80,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedTextColor = MaterialTheme.colorScheme.onSurface
        ),
        modifier = Modifier
            .fillMaxWidth()
    )
}

@Composable
fun SuggestionsDropdown(
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
                SuggestionItem(
                    text = topic,
                    onClick = { onTopicSelected(topic) }
                )
            }

            // Show "Create" option if no exact match exists
            val exactMatchExists = topics.any { it.equals(query, ignoreCase = true) }
            if (query.isNotBlank() && !exactMatchExists) {
                item {
                    SuggestionCreateItem(
                        query = query,
                        onCreate = onCreateTopic
                    )
                }
            }
        }
    }
}

@Composable
fun SuggestionItem(
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
fun SuggestionCreateItem(
    query: String,
    onCreate: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onCreate)
            .padding(12.dp)
    ) {
        Text(text = "+ Create '$query'", style = MaterialTheme.typography.bodyMedium)
    }
}




