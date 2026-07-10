package com.example.testtextmmo.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Animation
import androidx.compose.material.icons.outlined.Cloud
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material.icons.outlined.TextFields
import androidx.compose.material.icons.outlined.TouchApp
import androidx.compose.material.icons.outlined.ViewCompact
import androidx.compose.material.icons.outlined.VolumeUp
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.testtextmmo.data.AppState
import com.example.testtextmmo.data.model.ColorTheme
import com.example.testtextmmo.data.model.TextColorPreset
import com.example.testtextmmo.data.model.ThemeMode
import com.example.testtextmmo.ui.components.GlassCard
import com.example.testtextmmo.ui.components.SectionHeader
import com.example.testtextmmo.ui.components.mmoTextFieldColors
import com.example.testtextmmo.ui.components.mmoFilterChipColors
import com.example.testtextmmo.ui.theme.paletteFor

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SettingsScreen(appState: AppState) {
    val settings = appState.settings

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        SectionHeader("Настройки", "Интерфейс и подключение")
        Spacer(Modifier.height(16.dp))

        SettingsGroup("Интерфейс", Icons.Outlined.Palette) {
            Text("Яркость", style = MaterialTheme.typography.labelLarge, modifier = Modifier.padding(bottom = 8.dp))
            FlowRow(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                ThemeMode.entries.forEach { mode ->
                    FilterChip(
                        selected = settings.themeMode == mode,
                        onClick = { appState.updateSettings { it.copy(themeMode = mode) } },
                        label = { Text(themeLabel(mode), maxLines = 1, overflow = TextOverflow.Ellipsis) },
                        shape = RoundedCornerShape(10.dp),
                        colors = mmoFilterChipColors()
                    )
                }
            }

            Spacer(Modifier.height(14.dp))
            Text("Цветовая схема", style = MaterialTheme.typography.labelLarge, modifier = Modifier.padding(bottom = 8.dp))
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                ColorTheme.entries.forEach { theme ->
                    ColorThemeSwatch(
                        theme = theme,
                        selected = settings.colorTheme == theme,
                        onClick = { appState.updateSettings { it.copy(colorTheme = theme) } }
                    )
                }
            }

            Spacer(Modifier.height(14.dp))
            Text("Цвет текста", style = MaterialTheme.typography.labelLarge, modifier = Modifier.padding(bottom = 8.dp))
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                TextColorPreset.entries.forEach { preset ->
                    TextColorSwatch(
                        preset = preset,
                        selected = settings.textColorPreset == preset,
                        onClick = { appState.updateSettings { it.copy(textColorPreset = preset) } }
                    )
                }
            }

            Spacer(Modifier.height(14.dp))
            SettingSlider("Размер текста", settings.fontScale, "${(settings.fontScale * 100).toInt()}%",
                { appState.updateSettings { s -> s.copy(fontScale = it) } }, 0.85f..1.35f)

            SettingToggle(Icons.Outlined.Animation, "Анимации", "Плавные переходы и фон",
                settings.animationsEnabled) { v -> appState.updateSettings { it.copy(animationsEnabled = v) } }

            SettingToggle(Icons.Outlined.TextFields, "Превью историй", "Описание на карточках",
                settings.showStoryPreviews) { v -> appState.updateSettings { it.copy(showStoryPreviews = v) } }

            SettingToggle(Icons.Outlined.ViewCompact, "Скрыть нижнее меню", "Двойной tap переключает",
                settings.bottomNavHidden) { v -> appState.updateSettings { it.copy(bottomNavHidden = v) } }
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
            OutlinedTextField(settings.httpBaseUrl, { v -> appState.updateSettings { it.copy(httpBaseUrl = v) } },
                Modifier.fillMaxWidth(), label = { Text("HTTP API") }, singleLine = true,
                shape = RoundedCornerShape(14.dp), colors = mmoTextFieldColors())
            Spacer(Modifier.height(10.dp))
            OutlinedTextField(settings.serverUrl, { v -> appState.updateSettings { it.copy(serverUrl = v) } },
                Modifier.fillMaxWidth(), label = { Text("WebSocket") }, singleLine = true,
                shape = RoundedCornerShape(14.dp), colors = mmoTextFieldColors())
        }

        Spacer(Modifier.height(20.dp))
        GlassCard(Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp)) {
                Text("Chronicles", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text("v1.1.0 · UI Preview", style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        Spacer(Modifier.height(16.dp))
    }
}

@Composable
private fun TextColorSwatch(preset: TextColorPreset, selected: Boolean, onClick: () -> Unit) {
    val swatchColor = preset.colorArgb?.let { androidx.compose.ui.graphics.Color(it) }
        ?: MaterialTheme.colorScheme.onBackground
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .size(width = 64.dp, height = 72.dp)
            .clip(RoundedCornerShape(12.dp))
            .border(
                if (selected) 2.dp else 1.dp,
                if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                RoundedCornerShape(12.dp)
            )
            .clickable(onClick = onClick)
            .padding(6.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(
                    if (preset == TextColorPreset.AUTO) {
                        Brush.linearGradient(
                            listOf(
                                MaterialTheme.colorScheme.onBackground,
                                MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    } else {
                        Brush.linearGradient(listOf(swatchColor, swatchColor))
                    }
                )
        )
        Text(
            preset.label,
            style = MaterialTheme.typography.labelSmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
private fun ColorThemeSwatch(theme: ColorTheme, selected: Boolean, onClick: () -> Unit) {
    val p = paletteFor(theme)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .size(width = 72.dp, height = 88.dp)
            .clip(RoundedCornerShape(12.dp))
            .border(
                if (selected) 2.dp else 1.dp,
                if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                RoundedCornerShape(12.dp)
            )
            .clickable(onClick = onClick)
            .padding(8.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Brush.linearGradient(listOf(p.primary, p.secondary, p.background)))
        )
        Text(
            theme.label, style = MaterialTheme.typography.labelSmall, maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(top = 4.dp)
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
private fun SettingToggle(icon: ImageVector, title: String, subtitle: String, checked: Boolean, onChange: (Boolean) -> Unit) {
    Column(Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(end = 10.dp))
            Column(Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.bodyLarge, maxLines = 2)
                Text(subtitle, style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 2)
            }
            Switch(checked, onChange, colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colorScheme.primary,
                checkedTrackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
            ))
        }
        HorizontalDivider(Modifier.padding(top = 8.dp), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
    }
}

@Composable
private fun SettingSlider(label: String, value: Float, valueLabel: String, onChange: (Float) -> Unit, range: ClosedFloatingPointRange<Float>) {
    Column(Modifier.padding(vertical = 6.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(label, style = MaterialTheme.typography.bodyLarge)
            Text(valueLabel, color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.SemiBold)
        }
        Slider(value, onChange, valueRange = range,
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.primary,
                activeTrackColor = MaterialTheme.colorScheme.secondary
            ))
        HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
    }
}

private fun themeLabel(mode: ThemeMode) = when (mode) {
    ThemeMode.SYSTEM -> "Система"
    ThemeMode.DARK -> "Тёмная"
    ThemeMode.LIGHT -> "Светлая"
}
