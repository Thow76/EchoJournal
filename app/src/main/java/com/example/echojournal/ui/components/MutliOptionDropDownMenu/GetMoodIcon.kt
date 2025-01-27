package com.example.echojournal.ui.components.MutliOptionDropDownMenu

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.example.echojournal.R
import com.example.echojournal.ui.theme.MoodColors

/**
 * Returns an appropriate icon based on the given label and option.
 *
 * @param label A string representing the category of the item (e.g., "Moods", "Topics").
 * @param option A string representing the specific item within the category (e.g., "Stressed", "Sad").
 * @return An ImageVector corresponding to the icon, or null if no icon is available for the given label or option.
 */
@Composable
fun getIcon(label: String, option: String): ImageVector? {
    return when (label) {
        "Moods" -> {
            // Check the option to return a mood-specific icon.
            when (option) {
                "Stressed" -> ImageVector.vectorResource(R.drawable.stressed_mood) // Icon for "Stressed" mood.
                "Sad"      -> ImageVector.vectorResource(R.drawable.sad_mood)      // Icon for "Sad" mood.
                "Neutral"  -> ImageVector.vectorResource(R.drawable.neutral_mood)  // Icon for "Neutral" mood.
                "Peaceful" -> ImageVector.vectorResource(R.drawable.peaceful_mood) // Icon for "Peaceful" mood.
                "Excited"  -> ImageVector.vectorResource(R.drawable.excited_mood)  // Icon for "Excited" mood.
                else -> null // Return null for unrecognized mood options.
            }
        }
        "Topics" -> null // No icons available for "Topics".
        else -> null // Return null for unsupported labels.
    }
}

/**
 * Returns the outlined version of the icon for a specific mood.
 *
 * @param option A string representing the mood option (e.g., "Stressed", "Sad").
 * @return An ImageVector corresponding to the outlined icon, or null if no icon is available for the given option.
 */
@Composable
fun getMoodIconOutline(option: String): ImageVector? {
    return when (option) {
        "Stressed" -> ImageVector.vectorResource(R.drawable.stressed_mood_outline) // Outlined icon for "Stressed".
        "Sad" -> ImageVector.vectorResource(R.drawable.sad_mood_outline)           // Outlined icon for "Sad".
        "Neutral" -> ImageVector.vectorResource(R.drawable.neutral_mood_outline)   // Outlined icon for "Neutral".
        "Peaceful" -> ImageVector.vectorResource(R.drawable.peaceful_mood_outline) // Outlined icon for "Peaceful".
        "Excited" -> ImageVector.vectorResource(R.drawable.excited_mood_outline)   // Outlined icon for "Excited".
        else -> null // Return null for unrecognized mood options.
    }
}

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




