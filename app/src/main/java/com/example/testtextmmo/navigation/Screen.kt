package com.example.testtextmmo.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Character : Screen("character")
    data object Settings : Screen("settings")
}

enum class BottomTab(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    HOME(Screen.Home.route, "Истории", Icons.Default.Home),
    CHARACTER(Screen.Character.route, "Герой", Icons.Default.Person),
    SETTINGS(Screen.Settings.route, "Настройки", Icons.Default.Settings)
}
