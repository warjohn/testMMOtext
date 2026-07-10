package com.example.testtextmmo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.testtextmmo.data.model.Story
import com.example.testtextmmo.data.model.StoryGenre
import com.example.testtextmmo.ui.theme.genreAccent
import com.example.testtextmmo.ui.theme.paletteFor
import com.example.testtextmmo.data.model.ColorTheme

data class StoryArt(
    val gradient: List<Color>,
    val emoji: String
)

private val storyArts = mapOf(
    "fantasy_1" to StoryArt(listOf(Color(0xFF4A148C), Color(0xFF7C5CFF), Color(0xFF1A237E)), "🏰"),
    "fantasy_2" to StoryArt(listOf(Color(0xFF1B5E20), Color(0xFF7C5CFF), Color(0xFF004D40)), "🐉"),
    "scifi_1" to StoryArt(listOf(Color(0xFF0D47A1), Color(0xFF00BCD4), Color(0xFF1A237E)), "🚀"),
    "horror_1" to StoryArt(listOf(Color(0xFF1A1A2E), Color(0xFFB71C1C), Color(0xFF0D0D0D)), "👻"),
    "mystery_1" to StoryArt(listOf(Color(0xFF37474F), Color(0xFFE8B86D), Color(0xFF263238)), "🔍"),
    "survival_1" to StoryArt(listOf(Color(0xFF33691E), Color(0xFF795548), Color(0xFF212121)), "🏕️"),
    "custom" to StoryArt(listOf(Color(0xFF5C6BC0), Color(0xFF7C5CFF), Color(0xFF3949AB)), "✍️"),
    "default" to StoryArt(listOf(Color(0xFF37474F), Color(0xFF546E7A), Color(0xFF263238)), "📖")
)

private fun artFor(story: Story): StoryArt {
    story.previewImageUri?.let {
        return storyArts[story.previewArtId] ?: storyArts["default"]!!
    }
    return storyArts[story.previewArtId] ?: storyArts["default"]!!
}

@Composable
fun StoryPreviewImage(
    story: Story,
    modifier: Modifier = Modifier,
    height: androidx.compose.ui.unit.Dp = 140.dp
) {
    val art = artFor(story)
    val palette = paletteFor(ColorTheme.ARCANE)
    val accent = genreAccent(story.genre, palette)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
    ) {
        if (story.previewImageUri != null) {
            AsyncImage(
                model = story.previewImageUri,
                contentDescription = story.title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(Color.Transparent, Color.Black.copy(alpha = 0.55f))
                        )
                    )
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.linearGradient(
                            colors = art.gradient + listOf(accent.copy(alpha = 0.4f))
                        )
                    )
            )
            Text(
                text = art.emoji,
                modifier = Modifier.align(Alignment.Center),
                style = MaterialTheme.typography.displayLarge,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

@Composable
fun LiveWorldPreview(
    previewArtId: String,
    genre: StoryGenre,
    modifier: Modifier = Modifier
) {
    val art = storyArts[previewArtId] ?: storyArts["default"]!!
    val palette = paletteFor(ColorTheme.ARCANE)
    val accent = genreAccent(genre, palette)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(Brush.linearGradient(art.gradient + listOf(accent.copy(alpha = 0.3f)))),
        contentAlignment = Alignment.Center
    ) {
        Text(text = art.emoji, style = MaterialTheme.typography.displayMedium)
    }
}
