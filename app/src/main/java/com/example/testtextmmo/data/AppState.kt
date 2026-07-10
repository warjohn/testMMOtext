package com.example.testtextmmo.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.testtextmmo.data.model.AppSettings
import com.example.testtextmmo.data.model.CharacterClass
import com.example.testtextmmo.data.model.CharacterRace
import com.example.testtextmmo.data.model.GameCharacter
import com.example.testtextmmo.data.model.Story
import com.example.testtextmmo.data.model.LiveWorldTemplate
import com.example.testtextmmo.data.model.StoryGenre
import com.example.testtextmmo.data.model.StoryStatus
import com.example.testtextmmo.data.model.StoryType
import java.util.UUID

class AppState {
    var settings by mutableStateOf(AppSettings())
    var currentCharacter by mutableStateOf<GameCharacter?>(null)
    var stories by mutableStateOf(MockData.sampleStories)
    var customRaces by mutableStateOf<List<CharacterRace>>(emptyList())
    var customClasses by mutableStateOf<List<CharacterClass>>(emptyList())

    fun allRaces(): List<CharacterRace> = MockData.races + customRaces
    fun allClasses(): List<CharacterClass> = MockData.classes + customClasses

    fun updateSettings(transform: (AppSettings) -> AppSettings) {
        settings = transform(settings)
    }

    fun toggleBottomNav() {
        updateSettings { it.copy(bottomNavHidden = !it.bottomNavHidden) }
    }

    fun setCharacter(character: GameCharacter) {
        currentCharacter = character
    }

    fun addStory(story: Story) {
        stories = listOf(story) + stories
    }

    fun addCustomRace(name: String, description: String, bonusStat: String) {
        if (name.isBlank()) return
        val race = CharacterRace(
            id = "custom_race_${UUID.randomUUID()}",
            name = name.trim(),
            description = description.ifBlank { "Собственная раса" },
            emoji = "✨",
            bonusStat = bonusStat.ifBlank { "Особое" },
            isCustom = true
        )
        customRaces = customRaces + race
    }

    fun addCustomClass(name: String, description: String, primaryStat: String) {
        if (name.isBlank()) return
        val cls = CharacterClass(
            id = "custom_class_${UUID.randomUUID()}",
            name = name.trim(),
            description = description.ifBlank { "Собственный класс" },
            emoji = "⚡",
            primaryStat = primaryStat.ifBlank { "Уникальный" },
            isCustom = true
        )
        customClasses = customClasses + cls
    }

    fun createCustomStory(title: String, subtitle: String, genre: StoryGenre, previewUri: String?) {
        addStory(
            Story(
                id = UUID.randomUUID().toString(),
                title = title,
                subtitle = subtitle,
                type = StoryType.CUSTOM,
                genre = genre,
                status = StoryStatus.NEW,
                progressPercent = 0,
                lastPlayedLabel = "Только что",
                chapterCount = 0,
                playerCount = 1,
                previewArtId = "custom",
                previewImageUri = previewUri
            )
        )
    }

    fun joinLiveWorld(world: LiveWorldTemplate) {
        addStory(
            Story(
                id = UUID.randomUUID().toString(),
                title = world.title,
                subtitle = world.subtitle,
                type = StoryType.AUTO,
                genre = world.genre,
                status = StoryStatus.NEW,
                progressPercent = 0,
                lastPlayedLabel = "Только что",
                chapterCount = world.chapters,
                playerCount = world.players,
                previewArtId = world.previewArtId
            )
        )
    }
}
