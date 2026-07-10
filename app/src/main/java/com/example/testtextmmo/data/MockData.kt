package com.example.testtextmmo.data

import com.example.testtextmmo.data.model.CharacterClass
import com.example.testtextmmo.data.model.CharacterRace
import com.example.testtextmmo.data.model.LiveWorldTemplate
import com.example.testtextmmo.data.model.Story
import com.example.testtextmmo.data.model.StoryGenre
import com.example.testtextmmo.data.model.StoryStatus
import com.example.testtextmmo.data.model.StoryType

object MockData {

    val races = listOf(
        CharacterRace("elf", "Эльф", "Древняя мудрость лесов", "🧝", "+2 Интеллект"),
        CharacterRace("human", "Человек", "Универсальный адаптант", "🧑", "+1 ко всем"),
        CharacterRace("dwarf", "Дварф", "Несокрушимая стойкость", "🧔", "+2 Выносливость"),
        CharacterRace("orc", "Орк", "Ярость предков", "👹", "+2 Сила"),
        CharacterRace("tiefling", "Тифлинг", "Кровь инферно", "😈", "+2 Харизма"),
        CharacterRace("dragonborn", "Драконорождённый", "Наследие древних", "🐉", "+2 Сила"),
        CharacterRace("gnome", "Гном", "Изобретатель хаоса", "🧑‍🔬", "+2 Интеллект"),
        CharacterRace("halfling", "Полурослик", "Удача на стороне", "🍀", "+2 Ловкость"),
        CharacterRace("undead", "Нежить", "Вернувшийся из мёртвых", "💀", "+2 Выносливость"),
        CharacterRace("angel", "Небожитель", "Свет высших сфер", "👼", "+2 Харизма"),
        CharacterRace("beastkin", "Зверолюд", "Дух первобытной природы", "🐺", "+2 Ловкость"),
        CharacterRace("construct", "Гolem", "Создание древней магии", "🤖", "+2 Выносливость")
    )

    val classes = listOf(
        CharacterClass("warrior", "Воин", "Мастер клинка и щита", "⚔️", "Сила"),
        CharacterClass("mage", "Маг", "Повелитель арканных тайн", "🔮", "Интеллект"),
        CharacterClass("rogue", "Плут", "Тень между светом и тьмой", "🗡️", "Ловкость"),
        CharacterClass("cleric", "Жрец", "Голос божественной воли", "✨", "Харизма"),
        CharacterClass("ranger", "Следопыт", "Охотник диких земель", "🏹", "Ловкость"),
        CharacterClass("paladin", "Паладин", "Щит слабых и светлых", "🛡️", "Сила"),
        CharacterClass("necromancer", "Некромант", "Повелитель теней", "☠️", "Интеллект"),
        CharacterClass("bard", "Бард", "Мастер слова и песни", "🎵", "Харизма"),
        CharacterClass("monk", "Монах", "Дисциплина тела и духа", "🥋", "Ловкость"),
        CharacterClass("warlock", "Колдун", "Договор с иными силами", "👁️", "Интеллект"),
        CharacterClass("druid", "Дruid", "Голос дикой природы", "🌿", "Выносливость"),
        CharacterClass("assassin", "Ассassin", "Смерть без предупреждения", "🥷", "Ловкость")
    )

    val liveWorlds = listOf(
        LiveWorldTemplate(
            "Хроники Этерии", "Живой мир, где каждый игрок меняет судьбу королевств",
            StoryGenre.FANTASY, 20, 3420, "fantasy_2"
        ),
        LiveWorldTemplate(
            "Неоновый Сector", "Киберпанк-метрополис с бесконечными интригами",
            StoryGenre.SCIFI, 15, 1890, "scifi_1"
        ),
        LiveWorldTemplate(
            "Туманный Берег", "Прибрежный город, где исчезают люди",
            StoryGenre.HORROR, 10, 756, "horror_1"
        ),
        LiveWorldTemplate(
            "Архив №7", "Детективное агентство параллельных миров",
            StoryGenre.MYSTERY, 12, 2100, "mystery_1"
        ),
        LiveWorldTemplate(
            "Зона «Пепел»", "Постапокалипсис: выживи или стань легендой",
            StoryGenre.SURVIVAL, 18, 980, "survival_1"
        )
    )

    val sampleStories = listOf(
        Story(
            id = "1",
            title = "Пепел Забытых Королевств",
            subtitle = "Ты — последний хранитель печати, и мир трещит по швам",
            type = StoryType.AUTO,
            genre = StoryGenre.FANTASY,
            status = StoryStatus.IN_PROGRESS,
            progressPercent = 42,
            lastPlayedLabel = "2 ч назад",
            chapterCount = 7,
            playerCount = 1284,
            previewArtId = "fantasy_1"
        ),
        Story(
            id = "2",
            title = "Протокол «Нова»",
            subtitle = "Колония на краю туманности теряет связь с Землёй",
            type = StoryType.AUTO,
            genre = StoryGenre.SCIFI,
            status = StoryStatus.NEW,
            progressPercent = 0,
            lastPlayedLabel = "Не начата",
            chapterCount = 12,
            playerCount = 892,
            previewArtId = "scifi_1"
        ),
        Story(
            id = "3",
            title = "Мой путь из пепла",
            subtitle = "История, которую я создаю сам — от деревни до трона",
            type = StoryType.CUSTOM,
            genre = StoryGenre.FANTASY,
            status = StoryStatus.IN_PROGRESS,
            progressPercent = 67,
            lastPlayedLabel = "Вчера",
            chapterCount = 15,
            playerCount = 1,
            previewArtId = "fantasy_2"
        ),
        Story(
            id = "4",
            title = "Дом на окраине",
            subtitle = "Каждую ночь в зеркале появляется кто-то другой",
            type = StoryType.AUTO,
            genre = StoryGenre.HORROR,
            status = StoryStatus.PAUSED,
            progressPercent = 18,
            lastPlayedLabel = "5 дн назад",
            chapterCount = 5,
            playerCount = 456,
            previewArtId = "horror_1"
        ),
        Story(
            id = "5",
            title = "Убийство в туманном порту",
            subtitle = "Три свидетеля, один труп, ноль улик",
            type = StoryType.AUTO,
            genre = StoryGenre.MYSTERY,
            status = StoryStatus.COMPLETED,
            progressPercent = 100,
            lastPlayedLabel = "Завершена",
            chapterCount = 8,
            playerCount = 2103,
            previewArtId = "mystery_1"
        ),
        Story(
            id = "6",
            title = "Последний рейд",
            subtitle = "Своя кампания: гильдия, данжи и легендарный лут",
            type = StoryType.CUSTOM,
            genre = StoryGenre.SURVIVAL,
            status = StoryStatus.NEW,
            progressPercent = 0,
            lastPlayedLabel = "Черновик",
            chapterCount = 0,
            playerCount = 1,
            previewArtId = "survival_1"
        )
    )
}
