package com.example.echojournal.ui.components.MutliOptionDropDownMenu

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.example.echojournal.R

@Composable
fun getMoodIconForDropDown(option: String): ImageVector? {
    return when (option) {
        "Stressed" -> ImageVector.vectorResource(R.drawable.stressed_mood)
        "Sad" -> ImageVector.vectorResource(R.drawable.sad_mood)
        "Neutral" -> ImageVector.vectorResource(R.drawable.neutral_mood)
        "Peaceful" -> ImageVector.vectorResource(R.drawable.peaceful_mood)
        "Excited" -> ImageVector.vectorResource(R.drawable.excited_mood)
        else -> ImageVector.vectorResource(R.drawable.hash_icon)
    }
}
