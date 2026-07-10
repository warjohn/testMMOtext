package com.example.testtextmmo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import com.example.testtextmmo.data.AppState
import com.example.testtextmmo.navigation.MmoApp
import com.example.testtextmmo.ui.theme.TestTextMMOTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val appState = remember { AppState() }
            val settings = appState.settings
            TestTextMMOTheme(
                themeMode = settings.themeMode,
                colorTheme = settings.colorTheme,
                textColorPreset = settings.textColorPreset,
                hideStatusBar = settings.hideStatusBar
            ) {
                val density = LocalDensity.current
                CompositionLocalProvider(
                    LocalDensity provides Density(
                        density = density.density,
                        fontScale = density.fontScale * settings.fontScale
                    )
                ) {
                    MmoApp(appState = appState)
                }
            }
        }
    }
}
