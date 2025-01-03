package com.example.echojournal.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// =============================
// Material Colors/Schemes
// =============================

object MaterialColors {

    // Surface Colors
    val SurfacePrimary100 = Color(0xFFFFFFFF) // Primary 100

    val SurfaceVariant = Color(0xFFE1E2EC) // Surface Variant - E1E2EC

    // Inverse On Surface
    val InverseOnSurfaceSecondary95 = Color(0xFFEEF0FF) // Secondary 95

    // On Surface - Neutral Variant
    val OnSurfaceNeutralVariant10 = Color(0xFF191A20) // Neutral Variant 10
    val OnSurfaceNeutralVariant30 = Color(0xFF40434F) // Neutral Variant 30

    // Outline - Neutral Variant
    val OutlineNeutralVariant50 = Color(0xFF6C7085) // Neutral Variant 50
    val OutlineVariantNeutralVariant80 = Color(0xFFC1C3CE) // Neutral Variant 80

    // Primary Colors
    val Primary30 = Color(0xFF004CB4) // Primary 30
    val PrimaryContainer50 = Color(0xFF1F70F5) // Primary Container - Primary 50
    val OnPrimary100 = Color(0xFFFFFFFF) // On Primary - Primary 100
    val OnPrimaryFixed10 = Color(0xFF001945) // On Primary Fixed - Primary 10
    val InversePrimarySecondary80 = Color(0xFFBAC6E9) // Inverse Primary - Secondary 80

    // Secondary Colors
    val Secondary30 = Color(0xFF3B4663) // Secondary 30
    val SecondaryContainer50 = Color(0xFF6B7796) // Secondary Container - Secondary 50

    // Surface Tints with Opacity
    val SurfaceTint12 = Color(0x1E475D92) // Surface Tint 12% - #475D92 with 12% opacity
    val SurfaceTint15 = Color(0x0D475D92) // Surface Tint 15% - #475D92 with 5% opacity (assuming 0.05 is correct)

    // Background Color
    val BackgroundNeutralVariant99 = Color(0xFFFCFDFE) // Neutral Variant 99

    // Error Colors
    val OnErrorContainer20 = Color(0xFF680014) // On Error Container - Error 20
    val ErrorContainer95 = Color(0xFFFFEDEC) // Error Container - Error 95
    val OnError100 = Color(0xFFFFFFFF) // On Error - Error 100
}

// =============================
// Palettes
// =============================

object Palettes {

    // Primary Palette
    val Primary10 = Color(0xFF001945)
    val Primary30 = Color(0xFF004CB4)
    val Primary40 = Color(0xFF0057CC)
    val Primary50 = Color(0xFF1F70F5)
    val Primary60 = Color(0xFF578CFF)
    val Primary90 = Color(0xFFD9E2FF)
    val Primary95 = Color(0xFFEEF0FF)
    val Primary100 = Color(0xFFFFFFFF)

    // Secondary Palette
    val Secondary30 = Color(0xFF3B4663)
    val Secondary50 = Color(0xFF6B7796)
    val Secondary70 = Color(0xFF9FABCD)
    val Secondary80 = Color(0xFFBAC6E9)
    val Secondary90 = Color(0xFFD9E2FF)
    val Secondary95 = Color(0xFFEEF0FF)

    // Neutral Variant Palette
    val NeutralVariant10 = Color(0xFF191A20)
    val NeutralVariant30 = Color(0xFF40434F)
    val NeutralVariant50 = Color(0xFF6C7085)
    val NeutralVariant80 = Color(0xFFC1C3CE)
    val NeutralVariant90 = Color(0xFFE0E1E7)
    val NeutralVariant99 = Color(0xFFFCFDFE)
}

// =============================
// Error Colors
// =============================

object ErrorColors {
    val Error20 = Color(0xFF680014)
    val Error95 = Color(0xFFFFEDEC)
    val Error100 = Color(0xFFFFFFFF)
}

// =============================
// Gradients
// =============================

object Gradients {

    // BG Gradient - Secondary 90 (40% opacity) to Secondary 95 (40% opacity)
    val BgStart = Color(0x66D9E2FF) // Secondary 90 with 40% opacity
    val BgEnd = Color(0x66EEF0FF)   // Secondary 95 with 40% opacity

    val BgGradient = Brush.linearGradient(
        colors = listOf(BgStart, BgEnd),
        start = androidx.compose.ui.geometry.Offset.Zero,
        end = androidx.compose.ui.geometry.Offset.Infinite)

    // BG-saturated Gradient - Secondary 90 to Secondary 95
    val BgSaturatedStart = Palettes.Secondary90
    val BgSaturatedEnd = Palettes.Secondary95

    val BgSaturateGradient = Brush.linearGradient(
        colors = listOf(BgSaturatedStart, BgSaturatedEnd),
        start = androidx.compose.ui.geometry.Offset.Zero,
        end = androidx.compose.ui.geometry.Offset.Infinite)

    // Button Gradient - Primary 60 to Primary 50
    val ButtonStart = Palettes.Primary60
    val ButtonEnd = Palettes.Primary50

    val ButtonGradient = Brush.linearGradient(
        colors = listOf(ButtonStart, ButtonEnd),
        start = androidx.compose.ui.geometry.Offset.Zero,
        end = androidx.compose.ui.geometry.Offset.Infinite)

    // Button-Pressed Gradient - Primary 60 to Primary 40
    val ButtonPressedStart = Palettes.Primary60
    val ButtonPressedEnd = Palettes.Primary40

    val ButtonPressedGradient = Brush.linearGradient(
        colors = listOf(ButtonPressedStart, ButtonPressedEnd),
        start = androidx.compose.ui.geometry.Offset.Zero,
        end = androidx.compose.ui.geometry.Offset.Infinite)


}

// =============================
// Mood Colors
// =============================

object MoodColors {

    // Stressed
    val Stressed25 = Color(0xFFF8EFEF)
    val Stressed35 = Color(0xFFE9C5C5)
    val Stressed80 = Color(0xFFDE3A3A)
    val Stressed95 = Color(0xFF6B0303)
    val StressedGradientStart = Color(0xFFF69193)
    val StressedGradientEnd = Color(0xFFED3A3A)

    // Sad
    val Sad25 = Color(0xFFEFF4F8)
    val Sad35 = Color(0xFFC5D8E9)
    val Sad80 = Color(0xFF3A8EDE)
    val Sad95 = Color(0xFF004585)
    val SadGradientStart = Color(0xFF7BBCFA)
    val SadGradientEnd = Color(0xFF2993F7)

    // Neutral
    val Neutral25 = Color(0xFFEEF7F3)
    val Neutral35 = Color(0xFFB9DDCB)
    val Neutral80 = Color(0xFF41B278)
    val Neutral95 = Color(0xFF0A5F33)
    val NeutralGradientStart = Color(0xFFC4F3DB)
    val NeutralGradientEnd = Color(0xFF71EBAC)

    // Peaceful
    val Peaceful25 = Color(0xFFF6F2F5)
    val Peaceful35 = Color(0xFFE1CEDB)
    val Peaceful80 = Color(0xFFBE3294)
    val Peaceful95 = Color(0xFF6C044D)
    val PeacefulGradientStart = Color(0xFFFCCDEE)
    val PeacefulGradientEnd = Color(0xFFF991E0)

    // Excited
    val Excited25 = Color(0xFFF5F2EF)
    val Excited35 = Color(0xFFDDD2C8)
    val Excited80 = Color(0xFFDB6C0B)
    val Excited95 = Color(0xFF723602)
    val ExcitedGradientStart = Color(0xFFF5CB6F)
    val ExcitedGradientEnd = Color(0xFFF6B01A)
}
