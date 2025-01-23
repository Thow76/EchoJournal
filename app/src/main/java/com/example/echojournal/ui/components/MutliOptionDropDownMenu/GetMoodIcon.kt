package com.example.echojournal.ui.components.MutliOptionDropDownMenu

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.example.echojournal.R
import com.example.echojournal.ui.theme.MoodColors

@Composable
fun getMoodIcon(option: String): ImageVector {
    return when (option) {
        "Stressed" -> ImageVector.vectorResource(R.drawable.stressed_mood)
        "Sad" -> ImageVector.vectorResource(R.drawable.sad_mood)
        "Neutral" -> ImageVector.vectorResource(R.drawable.neutral_mood)
        "Peaceful" -> ImageVector.vectorResource(R.drawable.peaceful_mood)
        "Excited" -> ImageVector.vectorResource(R.drawable.excited_mood)
        else -> ImageVector.vectorResource(R.drawable.hash_icon)
    }
}


@Composable
fun getMoodIconOutline(option: String): ImageVector? {
    return when (option) {
        "Stressed" -> ImageVector.vectorResource(R.drawable.stressed_mood_outline)
        "Sad" -> ImageVector.vectorResource(R.drawable.sad_mood_outline)
        "Neutral" -> ImageVector.vectorResource(R.drawable.neutral_mood_outline)
        "Peaceful" -> ImageVector.vectorResource(R.drawable.peaceful_mood_outline)
        "Excited" -> ImageVector.vectorResource(R.drawable.excited_mood_outline)
        else -> null
    }
}

/**
 * Returns a Pair of (playbarColor, sliderColor) based on the mood
 * You can customize these to your liking or inline the logic directly.
 */
@Composable
fun getMoodColors(mood: String?): Triple<Color, Color, Color> {
    return when (mood) {
        "Neutral" -> Triple(MoodColors.Neutral35, MoodColors.Neutral25, MoodColors.Neutral80)
        "Stressed" -> Triple(MoodColors.Stressed35, MoodColors.Stressed25, MoodColors.Stressed80)
        "Sad" -> Triple(MoodColors.Sad35, MoodColors.Sad25, MoodColors.Sad80)
        "Peaceful" -> Triple(MoodColors.Peaceful35, MoodColors.Peaceful25, MoodColors.Peaceful80)
        "Excited" -> Triple(MoodColors.Excited35, MoodColors.Excited25, MoodColors.Excited80)
        else -> Triple(
            MaterialTheme.colorScheme.inversePrimary,
            MaterialTheme.colorScheme.inverseOnSurface,
            MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun getIcon(label: String, option: String): ImageVector? {
    return when (label) {
        "Moods" -> {
            // Return the appropriate mood icon based on the option
            when (option) {
                "Stressed" -> ImageVector.vectorResource(R.drawable.stressed_mood)
                "Sad"      -> ImageVector.vectorResource(R.drawable.sad_mood)
                "Neutral"  -> ImageVector.vectorResource(R.drawable.neutral_mood)
                "Peaceful" -> ImageVector.vectorResource(R.drawable.peaceful_mood)
                "Excited"  -> ImageVector.vectorResource(R.drawable.excited_mood)
                else -> null // No icon for unrecognized mood options
            }
        }
        "Topics" -> null // No icon for topics
        else -> null // No icon for other labels
    }
}


