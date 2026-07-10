package com.example.testtextmmo.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.testtextmmo.data.LpcAssetCatalog
import com.example.testtextmmo.data.model.CharacterAppearance
import com.example.testtextmmo.data.rememberLpcSprite

@Composable
fun PixelCharacterView(
    appearance: CharacterAppearance,
    modifier: Modifier = Modifier
) {
    val layers = remember(appearance) { LpcAssetCatalog.resolveLayers(appearance) }
    val bitmaps = layers.map { rememberLpcSprite(it) }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF1A1228))
            .border(2.dp, Color(0xFF4A3866), RoundedCornerShape(12.dp))
    ) {
        Canvas(Modifier.fillMaxSize()) {
            val frame = LpcAssetCatalog.FRAME.toFloat()
            val scale = minOf(size.width, size.height) / (frame * 1.35f)
            val drawSize = frame * scale
            val ox = (size.width - drawSize) / 2f
            val oy = (size.height - drawSize) / 2f

            bitmaps.forEach { bitmap ->
                bitmap?.let {
                    drawLpcFrame(
                        bitmap = it,
                        topLeft = Offset(ox, oy),
                        drawSize = drawSize
                    )
                }
            }
        }
    }
}

private fun DrawScope.drawLpcFrame(bitmap: ImageBitmap, topLeft: Offset, drawSize: Float) {
    val frame = LpcAssetCatalog.FRAME
    val srcX = LpcAssetCatalog.IDLE_COL * frame
    val srcY = LpcAssetCatalog.IDLE_ROW * frame
    drawImage(
        image = bitmap,
        srcOffset = IntOffset(srcX, srcY),
        srcSize = IntSize(frame, frame),
        dstOffset = IntOffset(topLeft.x.toInt(), topLeft.y.toInt()),
        dstSize = IntSize(drawSize.toInt(), drawSize.toInt())
    )
}

@Composable
fun PixelAvatarPreview(appearance: CharacterAppearance, modifier: Modifier = Modifier) {
    PixelCharacterView(appearance, modifier.size(168.dp))
}

@Composable
fun IdleGameAvatar(appearance: CharacterAppearance, modifier: Modifier = Modifier) =
    PixelCharacterView(appearance, modifier)

@Composable
fun IdleAvatarPreview(appearance: CharacterAppearance, modifier: Modifier = Modifier) =
    PixelAvatarPreview(appearance, modifier)
