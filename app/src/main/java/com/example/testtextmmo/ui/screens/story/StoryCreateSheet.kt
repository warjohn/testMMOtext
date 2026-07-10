package com.example.testtextmmo.ui.screens.story

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.testtextmmo.data.AppState
import com.example.testtextmmo.data.MockData
import com.example.testtextmmo.data.model.StoryGenre
import com.example.testtextmmo.ui.components.GlassCard
import com.example.testtextmmo.ui.components.GradientButton
import com.example.testtextmmo.ui.components.LiveWorldPreview
import com.example.testtextmmo.ui.components.OutlineAccentButton
import com.example.testtextmmo.ui.components.StoryPreviewImage
import com.example.testtextmmo.ui.components.mmoTextFieldColors
import com.example.testtextmmo.data.model.Story
import com.example.testtextmmo.data.model.StoryStatus
import com.example.testtextmmo.data.model.StoryType

enum class StorySheetType { CUSTOM, LIVE_WORLD }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoryCreateSheet(
    type: StorySheetType,
    appState: AppState,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.97f)
    ) {
        when (type) {
            StorySheetType.CUSTOM -> CustomStoryForm(appState = appState, onCreated = onDismiss)
            StorySheetType.LIVE_WORLD -> LiveWorldPicker(appState = appState, onJoined = onDismiss)
        }
    }
}

@Composable
private fun CustomStoryForm(appState: AppState, onCreated: () -> Unit) {
    var title by rememberSaveable { mutableStateOf("") }
    var subtitle by rememberSaveable { mutableStateOf("") }
    var genre by rememberSaveable { mutableStateOf(StoryGenre.FANTASY) }
    var previewUri by rememberSaveable { mutableStateOf<String?>(null) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        previewUri = uri?.toString()
    }

    Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)) {
        Text(
            text = "✍️ Моя история",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Создай авторскую кампанию",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Название") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(14.dp),
            colors = mmoTextFieldColors()
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = subtitle,
            onValueChange = { subtitle = it },
            label = { Text("Описание") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = mmoTextFieldColors()
        )
        Spacer(modifier = Modifier.height(12.dp))

        Text("Жанр", style = MaterialTheme.typography.labelLarge, modifier = Modifier.padding(bottom = 8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            StoryGenre.entries.forEach { g ->
                FilterChip(
                    selected = genre == g,
                    onClick = { genre = g },
                    label = { Text(g.label) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.25f)
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
        OutlineAccentButton(
            text = if (previewUri != null) "Обложка выбрана ✓" else "Выбрать обложку из галереи",
            onClick = { galleryLauncher.launch("image/*") }
        )

        if (previewUri != null || title.isNotBlank()) {
            Spacer(modifier = Modifier.height(12.dp))
            GlassCard(modifier = Modifier.fillMaxWidth()) {
                StoryPreviewImage(
                    story = Story(
                        id = "preview", title = title.ifBlank { "Новая история" },
                        subtitle = subtitle, type = StoryType.CUSTOM, genre = genre,
                        status = StoryStatus.NEW, progressPercent = 0,
                        lastPlayedLabel = "", chapterCount = 0,
                        previewArtId = "custom", previewImageUri = previewUri
                    ),
                    height = 120.dp
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        GradientButton(
            text = "Создать историю",
            onClick = {
                appState.createCustomStory(
                    title = title.ifBlank { "Без названия" },
                    subtitle = subtitle.ifBlank { "Авторская история" },
                    genre = genre,
                    previewUri = previewUri
                )
                onCreated()
            },
            enabled = title.trim().length >= 2
        )
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun LiveWorldPicker(appState: AppState, onJoined: () -> Unit) {
    Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)) {
        Text(
            text = "🌍 Живой мир",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Выбери мир, который развивается вместе с другими игроками",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(MockData.liveWorlds) { world ->
                GlassCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            appState.joinLiveWorld(world)
                            onJoined()
                        }
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        LiveWorldPreview(previewArtId = world.previewArtId, genre = world.genre)
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(world.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Text(
                            world.subtitle,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            "${world.players} игроков · ${world.chapters} глав",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.padding(top = 6.dp)
                        )
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}
