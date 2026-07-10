package com.example.testtextmmo.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import com.example.testtextmmo.data.model.ColorTheme

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

private fun ThemePalette.derived(): ThemePalette = copy(
    onBackground = contrastTextOn(background),
    onSurface = contrastTextOn(surface),
    onSurfaceVariant = contrastMutedOn(surfaceVariant),
    outline = contrastOutlineOn(surface)
)

fun paletteFor(theme: ColorTheme): ThemePalette = when (theme) {
    ColorTheme.ARCANE -> ThemePalette(
        primary = Color(0xFF7C5CFF), secondary = Color(0xFF3DD6C6), tertiary = Color(0xFFE8B86D),
        background = Color(0xFF0A0B14), surface = Color(0xFF12131F), surfaceVariant = Color(0xFF1E2138),
        onBackground = Color(0xFFF4F0FF), onSurface = Color(0xFFF4F0FF), onSurfaceVariant = Color(0xFFB8B2D0),
        outline = Color(0x28FFFFFF)
    ).derived()
    ColorTheme.DRACULA -> ThemePalette(
        primary = Color(0xFFBD93F9), secondary = Color(0xFF8BE9FD), tertiary = Color(0xFFFFB86C),
        background = Color(0xFF282A36), surface = Color(0xFF343746), surfaceVariant = Color(0xFF44475A),
        onBackground = Color(0xFFF8F8F2), onSurface = Color(0xFFF8F8F2), onSurfaceVariant = Color(0xFFA9B4D4),
        outline = Color(0x33BD93F9)
    ).derived()
    ColorTheme.MONOKAI -> ThemePalette(
        primary = Color(0xFFA6E22E), secondary = Color(0xFF66D9EF), tertiary = Color(0xFFFD971F),
        background = Color(0xFF272822), surface = Color(0xFF2F3129), surfaceVariant = Color(0xFF3E3D32),
        onBackground = Color(0xFFF8F8F2), onSurface = Color(0xFFF8F8F2), onSurfaceVariant = Color(0xFFCCC9B8),
        outline = Color(0x33A6E22E)
    ).derived()
    ColorTheme.NORD -> ThemePalette(
        primary = Color(0xFF88C0D0), secondary = Color(0xFF81A1C1), tertiary = Color(0xFFEBCB8B),
        background = Color(0xFF2E3440), surface = Color(0xFF3B4252), surfaceVariant = Color(0xFF434C5E),
        onBackground = Color(0xFFECEFF4), onSurface = Color(0xFFECEFF4), onSurfaceVariant = Color(0xFFD8DEE9),
        outline = Color(0x334C566A)
    ).derived()
    ColorTheme.ONE_DARK -> ThemePalette(
        primary = Color(0xFF61AFEF), secondary = Color(0xFF98C379), tertiary = Color(0xFFE5C07B),
        background = Color(0xFF21252B), surface = Color(0xFF282C34), surfaceVariant = Color(0xFF353B45),
        onBackground = Color(0xFFE6E8EB), onSurface = Color(0xFFE6E8EB), onSurfaceVariant = Color(0xFFABB2BF),
        outline = Color(0x3361AFEF)
    ).derived()
    ColorTheme.TOKYO_NIGHT -> ThemePalette(
        primary = Color(0xFF7AA2F7), secondary = Color(0xFF7DCFFF), tertiary = Color(0xFFFF9E64),
        background = Color(0xFF1A1B26), surface = Color(0xFF24283B), surfaceVariant = Color(0xFF292E42),
        onBackground = Color(0xFFC0CAF5), onSurface = Color(0xFFC0CAF5), onSurfaceVariant = Color(0xFFA9B1D6),
        outline = Color(0x337AA2F7)
    ).derived()
    ColorTheme.GRUVBOX -> ThemePalette(
        primary = Color(0xFFFE8019), secondary = Color(0xFF83A598), tertiary = Color(0xFFFABD2F),
        background = Color(0xFF282828), surface = Color(0xFF32302F), surfaceVariant = Color(0xFF3C3836),
        onBackground = Color(0xFFEBDBB2), onSurface = Color(0xFFEBDBB2), onSurfaceVariant = Color(0xFFD5C4A1),
        outline = Color(0x33FE8019)
    ).derived()
    ColorTheme.CATPPUCCIN -> ThemePalette(
        primary = Color(0xFFCBA6F7), secondary = Color(0xFF89B4FA), tertiary = Color(0xFFF9E2AF),
        background = Color(0xFF1E1E2E), surface = Color(0xFF181825), surfaceVariant = Color(0xFF313244),
        onBackground = Color(0xFFCDD6F4), onSurface = Color(0xFFCDD6F4), onSurfaceVariant = Color(0xFFB4BEFE),
        outline = Color(0x33CBA6F7)
    ).derived()
    ColorTheme.SOLARIZED -> ThemePalette(
        primary = Color(0xFF268BD2), secondary = Color(0xFF2AA198), tertiary = Color(0xFFB58900),
        background = Color(0xFF002B36), surface = Color(0xFF073642), surfaceVariant = Color(0xFF0A4050),
        onBackground = Color(0xFFEEE8D5), onSurface = Color(0xFFEEE8D5), onSurfaceVariant = Color(0xFF93A1A1),
        outline = Color(0x33268BD2)
    ).derived()
    ColorTheme.GITHUB -> ThemePalette(
        primary = Color(0xFF0969DA), secondary = Color(0xFF1A7F37), tertiary = Color(0xFFBF8700),
        background = Color(0xFFFFFFFF), surface = Color(0xFFF6F8FA), surfaceVariant = Color(0xFFEAEEF2),
        onBackground = Color(0xFF1F2328), onSurface = Color(0xFF1F2328), onSurfaceVariant = Color(0xFF424A53),
        outline = Color(0x330969DA)
    ).derived()
    ColorTheme.ROSE_PINE -> ThemePalette(
        primary = Color(0xFFC4A7E7), secondary = Color(0xFF9CCFD8), tertiary = Color(0xFFF6C177),
        background = Color(0xFF191724), surface = Color(0xFF1F1D2E), surfaceVariant = Color(0xFF26233A),
        onBackground = Color(0xFFE0DEF4), onSurface = Color(0xFFE0DEF4), onSurfaceVariant = Color(0xFF908CAA),
        outline = Color(0x33C4A7E7)
    ).derived()
    ColorTheme.CYBERPUNK -> ThemePalette(
        primary = Color(0xFFFF2A6D), secondary = Color(0xFF05D9E8), tertiary = Color(0xFFD1F7FF),
        background = Color(0xFF0D0221), surface = Color(0xFF150734), surfaceVariant = Color(0xFF1A0B3B),
        onBackground = Color(0xFFF0F0F0), onSurface = Color(0xFFF0F0F0), onSurfaceVariant = Color(0xFFD4C4E8),
        outline = Color(0x33FF2A6D)
    ).derived()
}

fun ThemePalette.toColorScheme(isDark: Boolean) = if (isDark) {
    darkColorScheme(
        primary = primary, onPrimary = Color.White,
        secondary = secondary, onSecondary = contrastTextOn(secondary),
        tertiary = tertiary, onTertiary = contrastTextOn(tertiary),
        background = background, onBackground = onBackground,
        surface = surface, onSurface = onSurface,
        surfaceVariant = surfaceVariant, onSurfaceVariant = onSurfaceVariant,
        outline = outline, error = Color(0xFFFF5C7A), onError = Color.White
    )
} else {
    lightColorScheme(
        primary = primary, onPrimary = Color.White,
        secondary = secondary, onSecondary = Color.White,
        tertiary = tertiary, onTertiary = contrastTextOn(tertiary),
        background = background, onBackground = onBackground,
        surface = surface, onSurface = onSurface,
        surfaceVariant = surfaceVariant, onSurfaceVariant = onSurfaceVariant,
        outline = outline, error = Color(0xFFFF5C7A), onError = Color.White
    )
}

fun genreAccent(genre: com.example.testtextmmo.data.model.StoryGenre, palette: ThemePalette): Color =
    when (genre) {
        com.example.testtextmmo.data.model.StoryGenre.FANTASY -> palette.primary
        com.example.testtextmmo.data.model.StoryGenre.SCIFI -> palette.secondary
        com.example.testtextmmo.data.model.StoryGenre.HORROR -> Color(0xFFFF5C7A)
        com.example.testtextmmo.data.model.StoryGenre.MYSTERY -> palette.tertiary
        com.example.testtextmmo.data.model.StoryGenre.SURVIVAL -> Color(0xFF4ADE80)
    }
