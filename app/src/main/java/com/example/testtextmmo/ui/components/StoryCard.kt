package com.example.testtextmmo.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.Pause
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.testtextmmo.data.model.Story
import com.example.testtextmmo.data.model.StoryStatus
import com.example.testtextmmo.data.model.StoryType
import com.example.testtextmmo.ui.theme.genreAccent
import com.example.testtextmmo.ui.theme.paletteFor
import com.example.testtextmmo.data.model.ColorTheme

@Composable
fun StoryCard(
    story: Story,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    showPreview: Boolean = true
) {
    val accent = genreAccent(story.genre, paletteFor(ColorTheme.ARCANE))
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.97f else 1f,
        label = "scale"
    )

    GlassCard(
        modifier = modifier
            .fillMaxWidth()
            .scale(scale)
            .clickable(interactionSource = interactionSource, indication = null, onClick = onClick),
        accentColor = accent,
        cornerRadius = 20.dp
    ) {
        Column {
            StoryPreviewImage(story = story, height = 130.dp)

            Column(modifier = Modifier.padding(18.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        GenreBadge(text = story.genre.label, color = accent)
                        TypeBadge(type = story.type)
                    }
                    StatusChip(status = story.status)
                }

                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = story.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                if (showPreview) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = story.subtitle,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            "${story.progressPercent}% · ${story.lastPlayedLabel}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        LinearProgressIndicator(
                            progress = { story.progressPercent / 100f },
                            modifier = Modifier.fillMaxWidth().height(5.dp).clip(RoundedCornerShape(3.dp)),
                            color = accent,
                            trackColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Surface(onClick = onClick, shape = RoundedCornerShape(12.dp), color = accent.copy(alpha = 0.2f)) {
                        Icon(
                            when (story.status) {
                                StoryStatus.NEW -> Icons.Default.PlayArrow
                                StoryStatus.PAUSED -> Icons.Outlined.Pause
                                else -> Icons.Default.AutoStories
                            },
                            null, tint = accent, modifier = Modifier.padding(10.dp).size(20.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                    MetaItem("${story.chapterCount} глав")
                    if (story.type == StoryType.AUTO) MetaItem(formatPlayers(story.playerCount))
                }
            }
        }
    }
}

@Composable
private fun GenreBadge(text: String, color: Color) {
    Surface(shape = RoundedCornerShape(8.dp), color = color.copy(alpha = 0.18f)) {
        Text(text, Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
            style = MaterialTheme.typography.labelSmall, color = color, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun TypeBadge(type: StoryType) {
    val (label, color) = when (type) {
        StoryType.CUSTOM -> "Авторская" to Color(0xFFE8B86D)
        StoryType.AUTO -> "Мир" to MaterialTheme.colorScheme.secondary
    }
    Surface(shape = RoundedCornerShape(8.dp), color = color.copy(alpha = 0.12f)) {
        Text(label, Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
            style = MaterialTheme.typography.labelSmall, color = color)
    }
}

@Composable
private fun StatusChip(status: StoryStatus) {
    val color = when (status) {
        StoryStatus.IN_PROGRESS -> MaterialTheme.colorScheme.secondary
        StoryStatus.NEW -> Color(0xFFE8B86D)
        StoryStatus.COMPLETED -> Color(0xFF4ADE80)
        StoryStatus.PAUSED -> MaterialTheme.colorScheme.onSurfaceVariant
    }
    Text(status.label, style = MaterialTheme.typography.labelSmall, color = color)
}

@Composable
private fun MetaItem(text: String) {
    Text(text, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
}

private fun formatPlayers(c: Int) = if (c >= 1000) "${c / 1000}k игроков" else "$c игроков"

@Composable
fun NewStoryCard(onCreateCustom: () -> Unit, onJoinAuto: () -> Unit, modifier: Modifier = Modifier) {
    GlassCard(modifier = modifier.fillMaxWidth(), accentColor = MaterialTheme.colorScheme.secondary) {
        Column(modifier = Modifier.padding(18.dp)) {
            Text("Начать приключение", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text("Создай свою историю или войди в живой мир",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 4.dp, bottom = 14.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                ActionTile("✍️", "Моя история", Color(0xFFE8B86D), onCreateCustom, Modifier.weight(1f))
                ActionTile("🌍", "Живой мир", MaterialTheme.colorScheme.secondary, onJoinAuto, Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun ActionTile(emoji: String, label: String, color: Color, onClick: () -> Unit, modifier: Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(Brush.horizontalGradient(listOf(color.copy(alpha = 0.3f), color.copy(alpha = 0.08f))))
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Column {
            Text(emoji, style = MaterialTheme.typography.headlineMedium)
            Text(label, style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.SemiBold)
        }
    }
}
