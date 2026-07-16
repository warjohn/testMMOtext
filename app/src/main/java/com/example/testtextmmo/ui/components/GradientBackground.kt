package com.example.testtextmmo.ui.components

import android.os.Build
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.example.testtextmmo.ui.theme.AppWallpaper

@Composable
fun rememberGifImageLoader(): ImageLoader {
    val context = LocalContext.current
    return remember(context) {
        ImageLoader.Builder(context)
            .components {
                if (Build.VERSION.SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }
            .build()
    }
}

/** Saturation > 1 makes colors richer; 1 = identity. */
fun vividWallpaperColorFilter(saturation: Float = 1.45f, contrast: Float = 1.12f): ColorFilter {
    val invSat = 1f - saturation
    val r = 0.213f * invSat
    val g = 0.715f * invSat
    val b = 0.072f * invSat
    val translate = 128f * (1f - contrast)
    val matrix = ColorMatrix(
        floatArrayOf(
            (r + saturation) * contrast, g * contrast, b * contrast, 0f, translate,
            r * contrast, (g + saturation) * contrast, b * contrast, 0f, translate,
            r * contrast, g * contrast, (b + saturation) * contrast, 0f, translate,
            0f, 0f, 0f, 1f, 0f
        )
    )
    return ColorFilter.colorMatrix(matrix)
}

@Composable
fun AnimatedGradientBackground(
    wallpaper: AppWallpaper = AppWallpaper.AURORA,
    modifier: Modifier = Modifier,
    animationsEnabled: Boolean = true
) {
    val context = LocalContext.current
    val imageLoader = rememberGifImageLoader()
    val vividFilter = remember { vividWallpaperColorFilter() }

    val transition = rememberInfiniteTransition(label = "bg")
    val rotation by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = if (animationsEnabled) 90_000 else 1,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )
    val drift by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = if (animationsEnabled) 24_000 else 1,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "drift"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF0B0918))
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(wallpaper.assetUri)
                .crossfade(false)
                .build(),
            imageLoader = imageLoader,
            contentDescription = wallpaper.label,
            contentScale = ContentScale.Crop,
            colorFilter = vividFilter,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    scaleX = 1.45f + drift * 0.08f
                    scaleY = 1.45f + drift * 0.08f
                    rotationZ = if (animationsEnabled) rotation else 0f
                }
        )

        // Soft bottom fade only — keeps text readable without washing out the wallpaper
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        0.0f to Color.Transparent,
                        0.55f to Color.Transparent,
                        1.0f to Color.Black.copy(alpha = 0.28f)
                    )
                )
        )
    }
}
