package com.example.testtextmmo.ui.theme

/**
 * Fantasy animated wallpapers stored in assets/backgrounds/.
 */
enum class AppWallpaper(
    val id: String,
    val label: String,
    val assetPath: String
) {
    AURORA("aurora", "Северное сияние", "backgrounds/fantasy_aurora.gif"),
    EMBERS("embers", "Угли магии", "backgrounds/fantasy_embers.gif"),
    MYSTIC("mystic", "Мистический туман", "backgrounds/fantasy_mystic.gif"),
    BLOOD_MOON("blood_moon", "Кровавая луна", "backgrounds/fantasy_blood_moon.gif"),
    COSMIC("cosmic", "Космическая туманность", "backgrounds/fantasy_cosmic.gif"),
    VOID("void", "Звёздная пустота", "backgrounds/fantasy_void.gif"),
    FOREST("forest", "Зачарованный лес", "backgrounds/fantasy_forest.gif"),
    STORM("storm", "Гроза и замок", "backgrounds/fantasy_storm.gif"),
    CRYSTAL("crystal", "Кристальная пещера", "backgrounds/fantasy_crystal.gif");

    val assetUri: String get() = "file:///android_asset/$assetPath"

    companion object {
        fun fromId(id: String): AppWallpaper =
            entries.firstOrNull { it.id == id } ?: AURORA
    }
}
