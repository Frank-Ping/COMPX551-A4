package com.example.polarh10activityviewer.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = DarkPrimary,
    onPrimary = DarkBackground,
    primaryContainer = Color(0xFF1E3A8A),
    onPrimaryContainer = Color(0xFFDBEAFE),
    secondary = DarkSecondaryText,
    onSecondary = DarkBackground,
    secondaryContainer = DarkSurface,
    onSecondaryContainer = DarkText,
    tertiary = DarkPrimary,
    onTertiary = DarkBackground,
    tertiaryContainer = Color(0xFF1E3A8A),
    onTertiaryContainer = Color(0xFFDBEAFE),
    background = DarkBackground,
    onBackground = DarkText,
    surface = DarkSurface,
    onSurface = DarkText,
    surfaceVariant = DarkSurface,
    onSurfaceVariant = DarkSecondaryText,
    surfaceTint = DarkPrimary,
    surfaceDim = DarkBackground,
    surfaceBright = DarkSurface,
    surfaceContainerLowest = DarkBackground,
    surfaceContainerLow = DarkSurface,
    surfaceContainer = DarkSurface,
    surfaceContainerHigh = DarkSurface,
    surfaceContainerHighest = DarkSurface,
    outline = DarkSecondaryText,
    outlineVariant = Color(0xFF475569),
    inverseSurface = LightSurface,
    inverseOnSurface = LightText,
    inversePrimary = LightPrimary
)

private val LightColorScheme = lightColorScheme(
    primary = LightPrimary,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFDBEAFE),
    onPrimaryContainer = Color(0xFF1E3A8A),
    secondary = LightSecondaryText,
    onSecondary = Color.White,
    secondaryContainer = LightBackground,
    onSecondaryContainer = LightText,
    tertiary = LightPrimary,
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFDBEAFE),
    onTertiaryContainer = Color(0xFF1E3A8A),
    background = LightBackground,
    onBackground = LightText,
    surface = LightSurface,
    onSurface = LightText,
    surfaceVariant = LightBackground,
    onSurfaceVariant = LightSecondaryText,
    surfaceTint = LightPrimary,
    surfaceDim = LightBackground,
    surfaceBright = LightSurface,
    surfaceContainerLowest = LightSurface,
    surfaceContainerLow = LightSurface,
    surfaceContainer = LightSurface,
    surfaceContainerHigh = LightSurface,
    surfaceContainerHighest = LightSurface,
    outline = LightSecondaryText,
    outlineVariant = Color(0xFFCBD5E1),
    inverseSurface = DarkSurface,
    inverseOnSurface = DarkText,
    inversePrimary = DarkPrimary
)

@Composable
fun PolarH10ActivityViewerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        typography = Typography,
        content = content
    )
}
