package com.example.testtextmmo.navigation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.waitForUpOrCancellation
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
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
import com.example.testtextmmo.ui.theme.AppWallpaper
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.ui.input.pointer.PointerEventPass

@Composable
fun MmoApp(appState: AppState) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    var storySheet by remember { mutableStateOf<StorySheetType?>(null) }

    val bottomNavVisible = !appState.settings.bottomNavHidden
    var renderBottomBar by remember { mutableStateOf(bottomNavVisible) }
    val navProgress = remember { Animatable(if (bottomNavVisible) 1f else 0f) }

    LaunchedEffect(bottomNavVisible) {
        if (bottomNavVisible) {
            renderBottomBar = true
            navProgress.animateTo(
                targetValue = 1f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMediumLow
                )
            )
        } else {
            navProgress.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 320, easing = FastOutSlowInEasing)
            )
            renderBottomBar = false
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedGradientBackground(
            wallpaper = AppWallpaper.fromId(appState.settings.wallpaperId),
            animationsEnabled = appState.settings.animationsEnabled
        )

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Transparent,
            bottomBar = {
                if (renderBottomBar) {
                    GlassBottomBar(
                        progress = navProgress.value,
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
                    // Observe double-taps without consuming — children keep normal clicks
                    .pointerInput(Unit) {
                        var lastUpTime = 0L
                        awaitEachGesture {
                            awaitFirstDown(requireUnconsumed = false, pass = PointerEventPass.Initial)
                            val up = waitForUpOrCancellation(pass = PointerEventPass.Initial) ?: return@awaitEachGesture
                            if (up.isConsumed) return@awaitEachGesture
                            val now = System.currentTimeMillis()
                            if (now - lastUpTime < 320L) {
                                appState.toggleBottomNav()
                                lastUpTime = 0L
                            } else {
                                lastUpTime = now
                            }
                        }
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
    progress: Float,
    currentRoute: String?,
    onTabSelected: (BottomTab) -> Unit
) {
    val density = LocalDensity.current
    val spreadPx = with(density) { 96.dp.toPx() }
    val sinkPx = with(density) { 56.dp.toPx() }
    val tabs = BottomTab.entries
    val centerIndex = (tabs.size - 1) / 2f
    val interactive = progress > 0.45f

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        tabs.forEachIndexed { index, tab ->
            val fromCenter = index - centerIndex
            BottomNavItem(
                label = tab.label,
                selected = currentRoute == tab.route,
                onClick = { if (interactive) onTabSelected(tab) },
                modifier = Modifier.graphicsLayer {
                    val p = progress.coerceIn(0f, 1f)
                    val reveal = FastOutSlowInEasing.transform(p)
                    alpha = reveal
                    scaleX = 0.2f + 0.8f * reveal
                    scaleY = 0.2f + 0.8f * reveal
                    // Collapse into / expand from the bottom-center point
                    translationX = (1f - reveal) * (-fromCenter) * spreadPx
                    translationY = (1f - reveal) * sinkPx
                    transformOrigin = TransformOrigin(0.5f, 1f)
                },
                icon = {
                    Icon(
                        imageVector = tab.icon,
                        contentDescription = tab.label,
                        tint = if (currentRoute == tab.route) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        }
                    )
                }
            )
        }
    }
}
