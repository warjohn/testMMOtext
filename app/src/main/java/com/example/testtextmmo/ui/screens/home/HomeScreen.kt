package com.example.testtextmmo.ui.screens.home

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.testtextmmo.data.AppState
import com.example.testtextmmo.data.model.CharacterAppearance
import com.example.testtextmmo.data.model.Story
import com.example.testtextmmo.data.model.StoryStatus
import com.example.testtextmmo.data.model.StoryType
import com.example.testtextmmo.ui.components.GlassCard
import com.example.testtextmmo.ui.components.IdleAvatarPreview
import com.example.testtextmmo.ui.components.NewStoryCard
import com.example.testtextmmo.ui.components.SectionHeader
import com.example.testtextmmo.ui.components.StoryCard
import com.example.testtextmmo.ui.components.mmoFilterChipColors
import com.example.testtextmmo.ui.theme.MysticCyan

private enum class StoryFilter(val label: String) {
    ALL("Все"),
    CUSTOM("Мои"),
    AUTO("Живые миры"),
    ACTIVE("В процессе"),
    DONE("Завершённые")
}

@Composable
fun HomeScreen(
    appState: AppState,
    onStoryClick: (Story) -> Unit,
    onCreateCustom: () -> Unit,
    onJoinAuto: () -> Unit
) {
    var selectedFilter by rememberSaveable { mutableStateOf(StoryFilter.ALL) }
    val character = appState.currentCharacter
    val filteredStories = appState.stories.filter { story ->
        when (selectedFilter) {
            StoryFilter.ALL -> true
            StoryFilter.CUSTOM -> story.type == StoryType.CUSTOM
            StoryFilter.AUTO -> story.type == StoryType.AUTO
            StoryFilter.ACTIVE -> story.status == StoryStatus.IN_PROGRESS || story.status == StoryStatus.NEW
            StoryFilter.DONE -> story.status == StoryStatus.COMPLETED
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            HeroSection(
                characterName = character?.name,
                appearance = character?.appearance
            )
        }

        item {
            NewStoryCard(
                onCreateCustom = onCreateCustom,
                onJoinAuto = onJoinAuto
            )
        }

        item {
            SectionHeader(
                title = "Твои истории",
                subtitle = "${filteredStories.size} из ${appState.stories.size}"
            )
        }

        item {
            FilterRow(
                selected = selectedFilter,
                onSelected = { selectedFilter = it }
            )
        }

        items(filteredStories, key = { it.id }) { story ->
            StoryCard(
                story = story,
                onClick = { onStoryClick(story) },
                showPreview = appState.settings.showStoryPreviews
            )
        }

        item { Spacer(modifier = Modifier.height(8.dp)) }
    }
}

@Composable
private fun HeroSection(
    characterName: String?,
    appearance: CharacterAppearance?
) {
    GlassCard(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = if (characterName != null) "С возвращением," else "Добро пожаловать,",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = characterName ?: "Странник",
                        style = MaterialTheme.typography.displayMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Твой мир ждёт следующую главу",
                        style = MaterialTheme.typography.bodySmall,
                        color = MysticCyan,
                        modifier = Modifier.padding(top = 6.dp)
                    )
                }
                if (appearance != null) {
                    IdleAvatarPreview(appearance = appearance, modifier = Modifier.size(80.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                MiniStat(
                    label = "Истории",
                    value = "6",
                    accent = MaterialTheme.colorScheme.primary
                )
                MiniStat(
                    label = "Миры",
                    value = "4",
                    accent = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.weight(1f)
                )
                MiniStat(
                    label = "Главы",
                    value = "47",
                    accent = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun MiniStat(
    label: String,
    value: String,
    accent: androidx.compose.ui.graphics.Color,
    modifier: Modifier = Modifier
) {
    GlassCard(
        modifier = modifier,
        cornerRadius = 14.dp,
        accentColor = accent
    ) {
        Column(modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)) {
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = accent
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun FilterRow(
    selected: StoryFilter,
    onSelected: (StoryFilter) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        StoryFilter.entries.forEach { filter ->
            FilterChip(
                selected = selected == filter,
                onClick = { onSelected(filter) },
                label = { Text(filter.label) },
                shape = RoundedCornerShape(12.dp),
                colors = mmoFilterChipColors()
            )
        }
    }
}
