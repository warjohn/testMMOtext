package com.example.testtextmmo.data.model

import androidx.compose.ui.graphics.Color

enum class StoryType(val label: String) {
    CUSTOM("Моя история"),
    AUTO("Живой мир")
}

enum class StoryGenre(val label: String) {
    FANTASY("Фэнтези"),
    SCIFI("Sci-Fi"),
    HORROR("Хоррор"),
    MYSTERY("Детектив"),
    SURVIVAL("Выживание")
}

enum class StoryStatus(val label: String) {
    IN_PROGRESS("В процессе"),
    NEW("Новая"),
    COMPLETED("Завершена"),
    PAUSED("На паузе")
}

data class CharacterStats(
    val strength: Int = 10,
    val agility: Int = 10,
    val intelligence: Int = 10,
    val charisma: Int = 10,
    val endurance: Int = 10
)

data class CharacterAppearance(
    val skinColor: Color = Color(0xFFE8B796),
    val faceType: Int = 7,
    val hairStyle: Int = 0,
    val hairColor: Color = Color(0xFF3D2314),
    val eyeStyle: Int = 0,
    val eyeColor: Color = Color(0xFF4A90D9),
    val mouthStyle: Int = 0,
    val topWear: Int = 0,
    val topColor: Color = Color(0xFF5C6BC0),
    val bottomWear: Int = 0,
    val bottomColor: Color = Color(0xFF37474F),
    val armorStyle: Int = -1,
    val armorColor: Color = Color(0xFF78909C),
    val accessoryStyle: Int = -1,
    val gloveColor: Color = Color(0xFF5D4037),
    val weaponStyle: Int = -1
)

data class GameCharacter(
    val id: String,
    val name: String,
    val race: String,
    val characterClass: String,
    val backstory: String,
    val stats: CharacterStats,
    val appearance: CharacterAppearance
)

data class Story(
    val id: String,
    val title: String,
    val subtitle: String,
    val type: StoryType,
    val genre: StoryGenre,
    val status: StoryStatus,
    val progressPercent: Int,
    val lastPlayedLabel: String,
    val chapterCount: Int,
    val playerCount: Int = 1,
    val previewArtId: String = "default",
    val previewImageUri: String? = null
)

data class AppSettings(
    val fontScale: Float = 1f,
    val animationsEnabled: Boolean = true,
    val wallpaperId: String = "aurora",
    val hapticFeedback: Boolean = true,
    val autoSave: Boolean = true,
    val showStoryPreviews: Boolean = true,
    val bottomNavHidden: Boolean = false,
    val hideStatusBar: Boolean = true,
    val serverUrl: String = "wss://api.mmotext.local",
    val httpBaseUrl: String = "https://api.mmotext.local",
    val notificationsEnabled: Boolean = true,
    val soundEffects: Boolean = true,
    val language: String = "ru"
)

data class CharacterRace(
    val id: String,
    val name: String,
    val description: String,
    val emoji: String,
    val bonusStat: String,
    val isCustom: Boolean = false
)

data class CharacterClass(
    val id: String,
    val name: String,
    val description: String,
    val emoji: String,
    val primaryStat: String,
    val isCustom: Boolean = false
)

data class LiveWorldTemplate(
    val title: String,
    val subtitle: String,
    val genre: StoryGenre,
    val chapters: Int,
    val players: Int,
    val previewArtId: String
)
