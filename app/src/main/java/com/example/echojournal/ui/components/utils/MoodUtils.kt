package com.example.echojournal.ui.components.utils

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.echojournal.ui.theme.MoodColors

/**
 * Returns a Triple of colors corresponding to the playbar, slider, and primary color for the given mood.
 *
 * @param mood A string representing the mood (e.g., "Neutral", "Stressed"). Nullable to handle undefined moods.
 * @return A Triple containing three Color objects: playbarColor, sliderColor, and primaryColor.
 *         Defaults to MaterialTheme colors for unrecognized or null moods.
 */
@Composable
fun getMoodColors(mood: String?): Triple<Color, Color, Color> {
    return when (mood) {
        "Neutral" -> Triple(
            MoodColors.Neutral35, // Playbar color for "Neutral".
            MoodColors.Neutral25, // Slider color for "Neutral".
            MoodColors.Neutral80  // Primary color for "Neutral".
        )
        "Stressed" -> Triple(
            MoodColors.Stressed35, // Playbar color for "Stressed".
            MoodColors.Stressed25, // Slider color for "Stressed".
            MoodColors.Stressed80  // Primary color for "Stressed".
        )
        "Sad" -> Triple(
            MoodColors.Sad35, // Playbar color for "Sad".
            MoodColors.Sad25, // Slider color for "Sad".
            MoodColors.Sad80  // Primary color for "Sad".
        )
        "Peaceful" -> Triple(
            MoodColors.Peaceful35, // Playbar color for "Peaceful".
            MoodColors.Peaceful25, // Slider color for "Peaceful".
            MoodColors.Peaceful80  // Primary color for "Peaceful".
        )
        "Excited" -> Triple(
            MoodColors.Excited35, // Playbar color for "Excited".
            MoodColors.Excited25, // Slider color for "Excited".
            MoodColors.Excited80  // Primary color for "Excited".
        )
        else -> Triple(
            MaterialTheme.colorScheme.inversePrimary,   // Default playbar color.
            MaterialTheme.colorScheme.inverseOnSurface, // Default slider color.
            MaterialTheme.colorScheme.primary           // Default primary color.
        )
    }
}