package com.example.testtextmmo.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.example.testtextmmo.ui.theme.ArcaneViolet
import com.example.testtextmmo.ui.theme.ArcaneVioletDim
import com.example.testtextmmo.ui.theme.MysticCyan
import com.example.testtextmmo.ui.theme.MysticCyanDim
import com.example.testtextmmo.ui.theme.NightSurface
import com.example.testtextmmo.ui.theme.VoidBlack

@Composable
fun AnimatedGradientBackground(
    modifier: Modifier = Modifier,
    animationsEnabled: Boolean = true
) {
    val transition = rememberInfiniteTransition(label = "bg")
    val offset by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = if (animationsEnabled) 12000 else 1, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "offset"
    )

    val dark = androidx.compose.material3.MaterialTheme.colorScheme.background.luminance() < 0.5f

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                if (dark) {
                    Brush.radialGradient(
                        colors = listOf(
                            ArcaneVioletDim.copy(alpha = 0.35f + offset * 0.1f),
                            VoidBlack,
                            NightSurface,
                            MysticCyanDim.copy(alpha = 0.12f)
                        ),
                        center = Offset(200f + offset * 300f, 100f + offset * 200f),
                        radius = 900f
                    )
                } else {
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFE8E0FF),
                            Color(0xFFF5F0FF),
                            Color(0xFFD4F5F0).copy(alpha = 0.5f)
                        )
                    )
                }
            )
    )
}

private fun Color.luminance(): Float {
    return 0.299f * red + 0.587f * green + 0.114f * blue
}
