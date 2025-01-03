package com.example.echojournal.ui.theme

import AppTypography
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable





    // App Theme
    @Composable
    fun EchoJournalTheme(
        content: @Composable () -> Unit
    ) {
        val appColorScheme = lightColorScheme(
            primary = Palettes.Primary30,
            onPrimary = MaterialColors.OnPrimary100,
            primaryContainer = MaterialColors.PrimaryContainer50,
            secondary = Palettes.Secondary30,
            onSecondary = Palettes.Secondary90,
            background = MaterialColors.BackgroundNeutralVariant99,
            surface = MaterialColors.SurfacePrimary100,
            surfaceVariant = MaterialColors.InverseOnSurfaceSecondary95,
            onSurface = MaterialColors.OnSurfaceNeutralVariant10,
            error = ErrorColors.Error95,
            onError = MaterialColors.OnError100
        )
        MaterialTheme(
            colorScheme = appColorScheme,
            typography = AppTypography,
            content = content
        )
    }