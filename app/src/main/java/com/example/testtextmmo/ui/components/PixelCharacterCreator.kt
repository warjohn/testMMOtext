package com.example.testtextmmo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.testtextmmo.data.LpcAsset
import com.example.testtextmmo.data.LpcAssetCatalog
import com.example.testtextmmo.data.model.CharacterAppearance

enum class PixelCreatorTab(val label: String) {
    BODY("Тело"), HAIR("Волосы"),
    TOP("Верх"), BOTTOM("Низ"), ARMOR("Броня"),
    WEAPON("Оружие"), ACCESSORY("Акс.")
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PixelCharacterCreator(
    appearance: CharacterAppearance,
    onChange: (CharacterAppearance) -> Unit,
    modifier: Modifier = Modifier
) {
    var tab by remember { mutableIntStateOf(0) }
    val currentTab = PixelCreatorTab.entries[tab]

    GlassCard(modifier = modifier.fillMaxWidth(), cornerRadius = 16.dp) {
        Column(Modifier.padding(12.dp)) {
            PixelCharacterView(
                appearance = appearance,
                modifier = Modifier.fillMaxWidth().height(220.dp)
            )

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.padding(vertical = 10.dp)
            ) {
                PixelCreatorTab.entries.forEachIndexed { index, t ->
                    FilterChip(
                        selected = tab == index,
                        onClick = { tab = index },
                        label = { Text(t.label, maxLines = 1) },
                        shape = RoundedCornerShape(10.dp),
                        colors = mmoFilterChipColors()
                    )
                }
            }

            when (currentTab) {
                PixelCreatorTab.BODY -> LpcPresetGrid(LpcAssetCatalog.bodies, appearance.faceType) {
                    onChange(appearance.copy(faceType = it))
                }
                PixelCreatorTab.HAIR -> LpcPresetGrid(LpcAssetCatalog.hair, appearance.hairStyle) {
                    onChange(appearance.copy(hairStyle = it))
                }
                PixelCreatorTab.TOP -> LpcPresetGrid(LpcAssetCatalog.torso, appearance.topWear) {
                    onChange(appearance.copy(topWear = it))
                }
                PixelCreatorTab.BOTTOM -> LpcPresetGrid(LpcAssetCatalog.legs, appearance.bottomWear) {
                    onChange(appearance.copy(bottomWear = it))
                }
                PixelCreatorTab.ARMOR -> LpcOptionalGrid(LpcAssetCatalog.armor, appearance.armorStyle) {
                    onChange(appearance.copy(armorStyle = it))
                }
                PixelCreatorTab.WEAPON -> LpcOptionalGrid(LpcAssetCatalog.weapons, appearance.weaponStyle) {
                    onChange(appearance.copy(weaponStyle = it))
                }
                PixelCreatorTab.ACCESSORY -> LpcOptionalGrid(LpcAssetCatalog.hats, appearance.accessoryStyle) {
                    onChange(appearance.copy(accessoryStyle = it))
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun LpcPresetGrid(
    options: List<LpcAsset>,
    selectedIndex: Int,
    onSelected: (Int) -> Unit
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        options.forEachIndexed { index, asset ->
            LpcChip(asset.label, index == selectedIndex) { onSelected(index) }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun LpcOptionalGrid(
    options: List<LpcAsset?>,
    selectedIndex: Int,
    onSelected: (Int) -> Unit
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        options.forEachIndexed { index, asset ->
            val label = asset?.label ?: "Нет"
            val selected = if (index == 0) selectedIndex < 0 else selectedIndex == index - 1
            LpcChip(label, selected) {
                onSelected(if (index == 0) -1 else index - 1)
            }
        }
    }
}

@Composable
private fun LpcChip(label: String, selected: Boolean, onClick: () -> Unit) {
    Box(
        Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(
                if (selected) MaterialTheme.colorScheme.primary.copy(alpha = 0.28f)
                else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            )
            .border(
                if (selected) 2.dp else 1.dp,
                if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                RoundedCornerShape(10.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 2
        )
    }
}
