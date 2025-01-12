package com.example.echojournal.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CustomButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    modifierIcon: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.small,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    size: Dp? = null,
    icon: ImageVector? = null,
    iconTint: Color = LocalContentColor.current.copy(alpha = LocalContentColor.current.alpha),
    contentDescription: String? = null,
    text: String? = null,
    textColor: Color = Color.White
) {
    // If a size is provided, apply it to the Modifier
    val buttonModifier = if (size != null) {
        modifier.size(size)
    } else {
        modifier
    }

    Button(
        onClick = onClick,
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor),
        contentPadding = PaddingValues(8.dp),
        modifier = buttonModifier
    ) {
        // 1. Icon (optional)
        if (icon != null) {
            Icon(
                modifier = modifierIcon.size(24.dp),
                imageVector = icon,
                contentDescription = contentDescription,
                tint = iconTint
            )
            // If we also have text, add a little space
            if (text != null) {
                Spacer(modifier = Modifier.width(8.dp))
            }
        }

        // 2. Text (optional)
        if (text != null) {
            Text(text = text, color = textColor)
        }
    }
}
