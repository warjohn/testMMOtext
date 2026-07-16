package com.example.testtextmmo.ui.screens.settings

import android.os.Build
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Animation
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Cloud
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material.icons.outlined.TextFields
import androidx.compose.material.icons.outlined.TouchApp
import androidx.compose.material.icons.outlined.ViewCompact
import androidx.compose.material.icons.outlined.VolumeUp
import androidx.compose.material.icons.outlined.Wallpaper
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.example.testtextmmo.data.AppState
import com.example.testtextmmo.ui.components.GlassCard
import com.example.testtextmmo.ui.components.SectionHeader
import com.example.testtextmmo.ui.components.mmoTextFieldColors
import com.example.testtextmmo.ui.components.vividWallpaperColorFilter
import com.example.testtextmmo.ui.theme.AppWallpaper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(appState: AppState) {
    val settings = appState.settings
    var showWallpaperPicker by remember { mutableStateOf(false) }
    val currentWallpaper = AppWallpaper.fromId(settings.wallpaperId)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        SectionHeader("Настройки", "Интерфейс и подключение")
        Spacer(Modifier.height(16.dp))

        SettingsGroup("Интерфейс", Icons.Outlined.Palette) {
            SettingSlider(
                "Размер текста",
                settings.fontScale,
                "${(settings.fontScale * 100).toInt()}%",
                { appState.updateSettings { s -> s.copy(fontScale = it) } },
                0.85f..1.35f
            )

            SettingActionRow(
                icon = Icons.Outlined.Wallpaper,
                title = "Обои фона",
                subtitle = currentWallpaper.label,
                actionLabel = "Выбрать"
            ) { showWallpaperPicker = true }

            SettingToggle(
                Icons.Outlined.Animation,
                "Анимации",
                "Плавные переходы и фон",
                settings.animationsEnabled
            ) { v -> appState.updateSettings { it.copy(animationsEnabled = v) } }

            SettingToggle(
                Icons.Outlined.TextFields,
                "Превью историй",
                "Описание на карточках",
                settings.showStoryPreviews
            ) { v -> appState.updateSettings { it.copy(showStoryPreviews = v) } }

            SettingToggle(
                Icons.Outlined.ViewCompact,
                "Скрыть нижнее меню",
                "Двойной tap переключает",
                settings.bottomNavHidden
            ) { v -> appState.updateSettings { it.copy(bottomNavHidden = v) } }
        }

        Spacer(Modifier.height(12.dp))
        SettingsGroup("Приложение", Icons.Outlined.TouchApp) {
            SettingToggle(Icons.Outlined.Save, "Автосохранение", "Сохранять прогресс", settings.autoSave) {
                appState.updateSettings { s -> s.copy(autoSave = it) }
            }
            SettingToggle(Icons.Outlined.TouchApp, "Тактильный отклик", "Вибрация", settings.hapticFeedback) {
                appState.updateSettings { s -> s.copy(hapticFeedback = it) }
            }
            SettingToggle(Icons.Outlined.Notifications, "Уведомления", "Новые главы", settings.notificationsEnabled) {
                appState.updateSettings { s -> s.copy(notificationsEnabled = it) }
            }
            SettingToggle(Icons.Outlined.VolumeUp, "Звуки", "Эффекты интерфейса", settings.soundEffects) {
                appState.updateSettings { s -> s.copy(soundEffects = it) }
            }
        }

        Spacer(Modifier.height(12.dp))
        SettingsGroup("Сервер", Icons.Outlined.Cloud) {
            OutlinedTextField(
                settings.httpBaseUrl,
                { v -> appState.updateSettings { it.copy(httpBaseUrl = v) } },
                Modifier.fillMaxWidth(),
                label = { Text("HTTP API") },
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                colors = mmoTextFieldColors()
            )
            Spacer(Modifier.height(10.dp))
            OutlinedTextField(
                settings.serverUrl,
                { v -> appState.updateSettings { it.copy(serverUrl = v) } },
                Modifier.fillMaxWidth(),
                label = { Text("WebSocket") },
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                colors = mmoTextFieldColors()
            )
        }

        Spacer(Modifier.height(20.dp))
        GlassCard(Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp)) {
                Text("Chronicles", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text(
                    "v1.1.0 · UI Preview",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        Spacer(Modifier.height(16.dp))
    }

    if (showWallpaperPicker) {
        WallpaperPickerSheet(
            selectedId = settings.wallpaperId,
            onSelect = { wallpaper ->
                appState.updateSettings { it.copy(wallpaperId = wallpaper.id) }
                showWallpaperPicker = false
            },
            onDismiss = { showWallpaperPicker = false }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WallpaperPickerSheet(
    selectedId: String,
    onSelect: (AppWallpaper) -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val context = LocalContext.current
    val imageLoader = remember(context) {
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

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 24.dp)
        ) {
            Text(
                "Обои фона",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                "Выберите анимированный фон — превью проигрывается сразу",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(480.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 8.dp)
            ) {
                items(AppWallpaper.entries, key = { it.id }) { wallpaper ->
                    val selected = wallpaper.id == selectedId
                    WallpaperPreviewCard(
                        wallpaper = wallpaper,
                        selected = selected,
                        imageLoader = imageLoader,
                        onClick = { onSelect(wallpaper) }
                    )
                }
            }

            TextButton(
                onClick = onDismiss,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Закрыть")
            }
        }
    }
}

@Composable
private fun WallpaperPreviewCard(
    wallpaper: AppWallpaper,
    selected: Boolean,
    imageLoader: ImageLoader,
    onClick: () -> Unit
) {
    val context = LocalContext.current
    val shape = RoundedCornerShape(16.dp)
    Column(
        modifier = Modifier
            .clip(shape)
            .border(
                width = if (selected) 2.dp else 1.dp,
                color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                shape = shape
            )
            .clickable(onClick = onClick)
    ) {
        Box {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(wallpaper.assetUri)
                    .crossfade(false)
                    .build(),
                imageLoader = imageLoader,
                contentDescription = wallpaper.label,
                contentScale = ContentScale.Crop,
                colorFilter = vividWallpaperColorFilter(),
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.72f)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            )
            if (selected) {
                Icon(
                    imageVector = Icons.Outlined.CheckCircle,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                )
            }
        }
        Text(
            text = wallpaper.label,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)
        )
    }
}

@Composable
private fun SettingsGroup(title: String, icon: ImageVector, content: @Composable () -> Unit) {
    GlassCard(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(icon, null, tint = MaterialTheme.colorScheme.secondary)
                Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            }
            Spacer(Modifier.height(12.dp))
            content()
        }
    }
}

@Composable
private fun SettingActionRow(
    icon: ImageVector,
    title: String,
    subtitle: String,
    actionLabel: String,
    onClick: () -> Unit
) {
    Column(Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(end = 10.dp))
            Column(Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.bodyLarge, maxLines = 2)
                Text(
                    subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2
                )
            }
            TextButton(onClick = onClick) {
                Text(actionLabel, fontWeight = FontWeight.SemiBold)
            }
        }
        HorizontalDivider(Modifier.padding(top = 8.dp), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
    }
}

@Composable
private fun SettingToggle(
    icon: ImageVector,
    title: String,
    subtitle: String,
    checked: Boolean,
    onChange: (Boolean) -> Unit
) {
    Column(Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(end = 10.dp))
            Column(Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.bodyLarge, maxLines = 2)
                Text(
                    subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2
                )
            }
            Switch(
                checked,
                onChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colorScheme.primary,
                    checkedTrackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                )
            )
        }
        HorizontalDivider(Modifier.padding(top = 8.dp), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
    }
}

@Composable
private fun SettingSlider(
    label: String,
    value: Float,
    valueLabel: String,
    onChange: (Float) -> Unit,
    range: ClosedFloatingPointRange<Float>
) {
    Column(Modifier.padding(vertical = 6.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(label, style = MaterialTheme.typography.bodyLarge)
            Text(valueLabel, color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.SemiBold)
        }
        Slider(
            value,
            onChange,
            valueRange = range,
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.primary,
                activeTrackColor = MaterialTheme.colorScheme.secondary
            )
        )
        HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
    }
}
