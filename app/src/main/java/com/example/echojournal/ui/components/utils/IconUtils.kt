package com.example.echojournal.ui.components.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.example.echojournal.R

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