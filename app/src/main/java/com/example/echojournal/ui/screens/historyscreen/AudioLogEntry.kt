package com.example.echojournal.ui.screens.historyscreen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.echojournal.viewmodels.AudioPlaybackUiState
import com.example.echojournal.model.JournalEntry
import com.example.echojournal.ui.components.audio.AudioPlayerBar
import com.example.echojournal.ui.components.topics.TopicsRow
import com.example.echojournal.ui.components.utils.getIcon
import com.example.echojournal.ui.components.utils.getMoodColors
import com.example.echojournal.viewmodels.AudioLogEntryViewModel
import com.example.echojournal.viewmodels.JournalHistoryViewModel

@Composable
fun AudioLogEntry(
    entry: JournalEntry,
    viewModel: JournalHistoryViewModel = hiltViewModel(),
    playbackViewModel: AudioLogEntryViewModel = hiltViewModel()
) {
    // Observe playback state map
    val uiStateMap by playbackViewModel.uiStateMap.collectAsState()

    // Extract the specific state for this entry
    val playbackUiState = uiStateMap[entry.id] ?: AudioPlaybackUiState()

    // Expand/collapse state
    val isExpanded = viewModel.isExpanded(entry.id)

    // Shortened description logic
    val maxChars = 130
    val shortDescription = if (entry.description.length > maxChars) {
        entry.description.substring(0, maxChars) + "..."
    } else {
        entry.description
    }

    // Load audio file when the file path changes
    LaunchedEffect(entry.audioFilePath) {
        entry.audioFilePath?.let { playbackViewModel.loadAudioFile(entry.id, it) }
    }

    // UI colors based on mood
    val (sliderColor, playbarColor, iconColor) = getMoodColors(entry.mood)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .animateContentSize(),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header row with icon, title, and timestamp
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    getIcon(label = "Moods", option = entry.mood)?.let {
                        Icon(
                            imageVector = it,
                            contentDescription = null,
                            tint = Color.Unspecified
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = entry.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    text = entry.timeStamp ?: "",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Audio Player Bar (if audio is present)
            if (entry.audioFilePath != null && playbackUiState.isFileLoaded) {
                AudioPlayerBar(
                    isPlaying = playbackUiState.isPlaybackActive,
                    currentPosition = playbackUiState.currentPosition,
                    duration = playbackUiState.duration ?: 0L,
                    onPlayPauseClicked = { playbackViewModel.togglePlayPause(entry.id) },
                    onSeek = { progress -> playbackViewModel.seekToPosition(entry.id, progress) },
                    playbarShape = RoundedCornerShape(16.dp),
                    iconColor = iconColor,
                    playbarColor = playbarColor,
                    sliderColor = sliderColor
                )
            } else {
                // Fallback for missing or unloaded audio
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = playbackUiState.errorMessage ?: "No Audio Available",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Expandable description
            if (entry.description.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .animateContentSize()
                        .fillMaxWidth()
                        .padding(top = 8.dp, start = 16.dp)
                ) {
                    val annotatedString = buildAnnotatedString {
                        append(
                            if (isExpanded) entry.description else shortDescription
                        )
                        if (entry.description.length > maxChars) {
                            if (!isExpanded) append("... ")
                            pushStringAnnotation(tag = "TOGGLE", annotation = "toggle")
                            withStyle(
                                style = SpanStyle(
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold
                                )
                            ) {
                                append(if (isExpanded) "Show Less" else "Show More")
                            }
                            pop()
                        }
                    }

                    ClickableText(
                        text = annotatedString,
                        style = MaterialTheme.typography.bodyMedium,
                        onClick = { offset ->
                            annotatedString.getStringAnnotations("TOGGLE", offset, offset)
                                .firstOrNull()?.let { viewModel.toggleExpanded(entry.id) }
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Topics Row (if topics exist)
            val topicsList = entry.topics ?: emptyList()
            if (topicsList.isNotEmpty()) {
                TopicsRow(topics = topicsList)
            } else {
                Text(
                    text = "No topics",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

