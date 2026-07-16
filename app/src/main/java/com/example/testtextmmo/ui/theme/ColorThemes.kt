package com.example.testtextmmo.ui.theme

import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import com.example.testtextmmo.data.model.StoryGenre

data class ThemePalette(
    val primary: Color,
    val secondary: Color,
    val tertiary: Color,
    val background: Color,
    val surface: Color,
    val surfaceVariant: Color,
    val onBackground: Color,
    val onSurface: Color,
    val onSurfaceVariant: Color,
    val outline: Color
)

private val BlackText = Color(0xFF1F2328)
private val BlackTextMuted = Color(0xFF424A53)

val DefaultPalette = ThemePalette(
    primary = Color(0xFF0969DA),
    secondary = Color(0xFF1A7F37),
    tertiary = Color(0xFFBF8700),
    background = Color(0xFFFFFFFF),
    surface = Color(0xFFF6F8FA),
    surfaceVariant = Color(0xFFEAEEF2),
    onBackground = BlackText,
    onSurface = BlackText,
    onSurfaceVariant = BlackTextMuted,
    outline = Color(0x330969DA)
)

fun ThemePalette.toColorScheme() = lightColorScheme(
    primary = primary,
    onPrimary = Color.White,
    secondary = secondary,
    onSecondary = Color.White,
    tertiary = tertiary,
    onTertiary = BlackText,
    background = background,
    onBackground = onBackground,
    surface = surface,
    onSurface = onSurface,
    surfaceVariant = surfaceVariant,
    onSurfaceVariant = onSurfaceVariant,
    outline = outline,
    error = Color(0xFFFF5C7A),
    onError = Color.White
)

fun genreAccent(genre: StoryGenre, palette: ThemePalette = DefaultPalette): Color =
    when (genre) {
        StoryGenre.FANTASY -> palette.primary
        StoryGenre.SCIFI -> palette.secondary
        StoryGenre.HORROR -> Color(0xFFFF5C7A)
        StoryGenre.MYSTERY -> palette.tertiary
        StoryGenre.SURVIVAL -> Color(0xFF4ADE80)
    }
