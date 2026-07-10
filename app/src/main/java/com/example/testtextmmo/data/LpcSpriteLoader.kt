package com.example.testtextmmo.data

import android.content.Context
import android.graphics.BitmapFactory
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext

object LpcSpriteLoader {
    private val cache = mutableMapOf<String, ImageBitmap>()

    fun load(context: Context, assetPath: String): ImageBitmap? {
        cache[assetPath]?.let { return it }
        return try {
            context.assets.open("lpc/$assetPath").use { stream ->
                BitmapFactory.decodeStream(stream)?.asImageBitmap()?.also { cache[assetPath] = it }
            }
        } catch (_: Exception) {
            null
        }
    }

    fun clearCache() {
        cache.clear()
    }
}

@Composable
fun rememberLpcSprite(assetPath: String): ImageBitmap? {
    val context = LocalContext.current
    return remember(assetPath) { LpcSpriteLoader.load(context, assetPath) }
}
