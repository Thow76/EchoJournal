package com.example.echojournal.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import com.yourapp.ui.theme.AppTypography




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
            onSecondary = MaterialColors.InversePrimarySecondary80,
            background = MaterialColors.BackgroundNeutralVariant99,
            surface = MaterialColors.SurfacePrimary100,
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