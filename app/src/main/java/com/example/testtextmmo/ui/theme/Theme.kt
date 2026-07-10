package com.example.testtextmmo.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.testtextmmo.data.model.ColorTheme
import com.example.testtextmmo.data.model.TextColorPreset
import com.example.testtextmmo.data.model.ThemeMode

@Composable
fun TestTextMMOTheme(
    themeMode: ThemeMode = ThemeMode.SYSTEM,
    colorTheme: ColorTheme = ColorTheme.ARCANE,
    textColorPreset: TextColorPreset = TextColorPreset.AUTO,
    hideStatusBar: Boolean = true,
    content: @Composable () -> Unit
) {
    val systemDark = isSystemInDarkTheme()
    val palette = paletteFor(colorTheme)
    val darkTheme = when (themeMode) {
        ThemeMode.DARK -> true
        ThemeMode.LIGHT -> false
        ThemeMode.SYSTEM -> systemDark
    }
    val useDarkPalette = when (themeMode) {
        ThemeMode.DARK -> true
        ThemeMode.LIGHT -> false
        ThemeMode.SYSTEM -> colorTheme.isDark || systemDark
    }
    val effectiveDark = if (colorTheme == ColorTheme.GITHUB && themeMode != ThemeMode.DARK) false else useDarkPalette

    val colorScheme = palette.toColorScheme(effectiveDark).let { scheme ->
        val override = textColorPreset.colorArgb?.let { Color(it) }
        if (override == null) scheme else scheme.copy(
            onBackground = override,
            onSurface = override,
            onSurfaceVariant = override.copy(alpha = 0.78f)
        )
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Color.Transparent.toArgb()
            window.navigationBarColor = Color.Transparent.toArgb()
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = !effectiveDark
                isAppearanceLightNavigationBars = !effectiveDark
                if (hideStatusBar) {
                    hide(WindowInsetsCompat.Type.statusBars())
                    systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                } else {
                    show(WindowInsetsCompat.Type.statusBars())
                }
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
