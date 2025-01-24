package com.example.echojournal.ui.screens.historyscreen

import android.media.MediaPlayer
import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.echojournal.R
import com.example.echojournal.model.JournalEntry
import com.example.echojournal.ui.components.AudioPlayerBar
import com.example.echojournal.ui.components.MutliOptionDropDownMenu.getMoodColors
import com.example.echojournal.ui.components.MutliOptionDropDownMenu.getMoodIcon
import com.example.echojournal.ui.screens.createentryscreen.TopicChip
import com.example.echojournal.ui.theme.MoodColors

@Composable
fun AudioLogEntry(entry: JournalEntry,
                  viewModel: JournalHistoryViewModel = hiltViewModel()) {

    val isExpanded = viewModel.isExpanded(entry.id)

    val maxChars = 130

    val shortDescription = if (entry.description.length > maxChars) {
        entry.description.substring(0, maxChars) + "..."
    } else {
        entry.description
    }

    // Audio playback states
    var isPlaying by remember { mutableStateOf(false) }
    var currentPosition by remember { mutableStateOf(0L) }
    var duration by remember { mutableStateOf(0L) }

    // Initialize MediaPlayer
    val context = LocalContext.current
    val mediaPlayer = remember(entry.audioFilePath) {
        entry.audioFilePath?.let {
            MediaPlayer().apply {    try {
            setDataSource(it)
            prepare()
            duration = this.duration.toLong()
        } catch (e: Exception) {
            Log.e("AudioLogEntry", "Error initializing MediaPlayer: ${e.message}")
        }
        }}
    }

    // Update currentPosition periodically when playing
    LaunchedEffect(isPlaying) {
        while (isPlaying && mediaPlayer != null) {
            currentPosition = mediaPlayer.currentPosition.toLong()
            kotlinx.coroutines.delay(1000L)
            // Stop playback if currentPosition reaches or exceeds duration
            if (currentPosition >= duration) {
                isPlaying = false
                mediaPlayer.seekTo(0)
                currentPosition = 0
            }
        }
    }

    // Release MediaPlayer when the composable leaves the composition
    DisposableEffect(mediaPlayer) {
        onDispose {
            mediaPlayer?.release()
        }
    }

    // Function to handle play/pause toggle
    fun togglePlayPause() {
        mediaPlayer?.let {
            if (isPlaying) {
                it.pause()
                isPlaying = false
            } else {
                it.start()
                isPlaying = true
            }
        }
    }

    // Function to handle seeking in the audio
    fun handleSeek(progress: Float) {
        mediaPlayer?.let {
            val newPosition = (progress * duration).toLong()
            it.seekTo(newPosition.toInt())
            currentPosition = newPosition
        }
    }

    // Use getMoodColors for consistency
    val (sliderColor, playbarColor, iconColor) = getMoodColors(entry.mood)

    // A Card that wraps the journal entry, with animateContentSize for smooth expands/collapses
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .animateContentSize()  // <-- Smooth height transitions
            .clickable { /* Handle card click if desired (optional) */ },
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
                    Icon(
                        imageVector = getMoodIcon(entry.mood),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
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
            if (entry.audioFilePath != null) {
                AudioPlayerBar(
                    isPlaying = isPlaying,
                    currentPosition = currentPosition,
                    duration = duration,
                    onPlayPauseClicked = { togglePlayPause() },
                    onSeek = { progress -> handleSeek(progress) },
                    playbarShape = RoundedCornerShape(16.dp),
                    iconColor = iconColor,
                    playbarColor = playbarColor,
                    sliderColor = sliderColor
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No Audio Available",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (entry.description.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .animateContentSize()
                        .fillMaxWidth()
                        .padding(top = 8.dp, start = 16.dp)
                ) {
                    val annotatedString = buildAnnotatedString {
                        append(
                            if (isExpanded) entry.description else entry.description.take(
                                maxChars
                            ).trim()
                        )
                        if (entry.description.length > maxChars) {
                            if (!isExpanded) append("... ")

                            // Add "Show More" or "Show Less" with independent style
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

                    // Display text with clickable span
                    ClickableText(
                        text = annotatedString,
                        style = MaterialTheme.typography.bodyMedium,
                        onClick = { offset ->
                            // Check if the "Show More" or "Show Less" was clicked
                            annotatedString.getStringAnnotations(
                                "TOGGLE",
                                start = offset,
                                end = offset
                            )
                                .firstOrNull()?.let {
                                    viewModel.toggleExpanded(entry.id)
                                }
                        }
                    )
                }
            }


            // Safely handle null or empty lists
            val topicsList = entry.topics ?: emptyList()

            if (topicsList.isNotEmpty()) {
                TopicsRow(topics = topicsList)
            } else {
                // Fallback if no topics (optional)
                Text(
                    text = "No topics",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        }
    }



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
