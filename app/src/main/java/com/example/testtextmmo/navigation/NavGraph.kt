package com.example.testtextmmo.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.testtextmmo.data.AppState
import com.example.testtextmmo.ui.components.AnimatedGradientBackground
import com.example.testtextmmo.ui.components.BottomNavItem
import com.example.testtextmmo.ui.screens.character.CharacterScreen
import com.example.testtextmmo.ui.screens.home.HomeScreen
import com.example.testtextmmo.ui.screens.settings.SettingsScreen
import com.example.testtextmmo.ui.screens.story.StoryCreateSheet
import com.example.testtextmmo.ui.screens.story.StorySheetType
import com.example.testtextmmo.ui.theme.ArcaneVioletLight

@Composable
fun MmoApp(appState: AppState) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    var storySheet by remember { mutableStateOf<StorySheetType?>(null) }

    val bottomNavHidden = appState.settings.bottomNavHidden
    val navEnter = expandVertically(
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessMediumLow
        ),
        expandFrom = Alignment.Bottom
    ) + fadeIn(tween(420, easing = FastOutSlowInEasing))
    val navExit = shrinkVertically(
        animationSpec = tween(320, easing = FastOutSlowInEasing),
        shrinkTowards = Alignment.Bottom
    ) + fadeOut(tween(260))

    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedGradientBackground(animationsEnabled = appState.settings.animationsEnabled)

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Transparent,
            bottomBar = {
                AnimatedVisibility(
                    visible = !bottomNavHidden,
                    enter = navEnter,
                    exit = navExit
                ) {
                    GlassBottomBar(
                        currentRoute = currentRoute,
                        onTabSelected = { tab ->
                            navController.navigate(tab.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .pointerInput(Unit) {
                        detectTapGestures(onDoubleTap = { appState.toggleBottomNav() })
                    }
            ) {
                NavHost(
                    navController = navController,
                    startDestination = Screen.Home.route,
                    modifier = Modifier.fillMaxSize(),
                    enterTransition = { fadeIn() },
                    exitTransition = { fadeOut() }
                ) {
                    composable(Screen.Home.route) {
                        HomeScreen(
                            appState = appState,
                            onStoryClick = { },
                            onCreateCustom = { storySheet = StorySheetType.CUSTOM },
                            onJoinAuto = { storySheet = StorySheetType.LIVE_WORLD }
                        )
                    }
                    composable(Screen.Character.route) {
                        CharacterScreen(appState = appState)
                    }
                    composable(Screen.Settings.route) {
                        SettingsScreen(appState = appState)
                    }
                }
            }
        }

        storySheet?.let { type ->
            StoryCreateSheet(
                type = type,
                appState = appState,
                onDismiss = { storySheet = null }
            )
        }
    }
}

@Composable
private fun GlassBottomBar(
    currentRoute: String?,
    onTabSelected: (BottomTab) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomTab.entries.forEach { tab ->
            BottomNavItem(
                label = tab.label,
                selected = currentRoute == tab.route,
                onClick = { onTabSelected(tab) },
                icon = {
                    Icon(
                        imageVector = tab.icon,
                        contentDescription = tab.label,
                        tint = if (currentRoute == tab.route) {
                            ArcaneVioletLight
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        }
                    )
                }
            )
        }
    }
}
