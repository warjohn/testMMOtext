package com.example.testtextmmo.data

/** Pixel layer definitions inspired by LPC / idle-RPG modular sprites (CC0-style procedural art). */
object PixelSpriteLibrary {
    const val GRID_W = 32
    const val GRID_H = 48

    val skinColors = CharacterPresets.skinColors
    val hairColors = CharacterPresets.hairColors
    val eyeColors = CharacterPresets.eyeColors
    val topColors = CharacterPresets.topColors
    val bottomColors = CharacterPresets.bottomColors
    val armorColors = CharacterPresets.armorColors

    val hairStyles = listOf(
        "Короткие", "Ёжик", "Прямые", "Кудри", "Хвост", "Коса", "Иroquois",
        "Лысый", "Длинные", "Боковой", "Дреды", "Пучок", "Рыцарские", "Эльфийские", "Пиксель"
    )
    val topStyles = listOf(
        "Футболка", "Рубашка", "Кожанка", "Роба мага", "Кольчуга", "Плащ",
        "Жилет", "Туника", "Латная", "Кимоно", "Халат", "Нагрудник", "Стёганка", "Герой EHT"
    )
    val bottomStyles = listOf(
        "Штаны", "Шорты", "Юбка", "Килт", "Леггинсы", "Роба", "Латы", "Шаровары", "Джинсы", "Доспехи"
    )
    val armorStyles = listOf("Нет", "Лёгкая", "Средняя", "Тяжёлая", "Магическая", "Королевская", "Теневая", "EHT")
    val weaponStyles = listOf(
        "Нет", "Меч", "Топор", "Лук", "Посох", "Кинжал", "Молот", "Копьё", "Щит", "Книга", "Безрук"
    )
    val accessoryStyles = CharacterPresets.accessoryStyles
    val faceTypes = CharacterPresets.faceTypes
    val eyeStyles = CharacterPresets.eyeStyles
    val mouthStyles = CharacterPresets.mouthStyles

    fun hairPixels(style: Int): List<Pair<Int, Int>> = when (style % 15) {
        0 -> rect(11, 4, 10, 4) + rect(10, 8, 12, 2)
        1 -> listOf(12 to 4, 13 to 3, 14 to 3, 15 to 3, 16 to 3, 17 to 3, 18 to 3, 19 to 4) +
            (10..21).flatMap { x -> listOf(x to 5, x to 6, x to 7) }
        2 -> rect(10, 5, 12, 3) + rect(9, 8, 14, 5)
        3 -> (10..21).flatMap { x -> (4..9).map { y -> x to y } } + rect(9, 10, 14, 3)
        4 -> rect(12, 4, 8, 5) + (12..19).flatMap { y -> listOf(10 to y, 21 to y) }
        5 -> rect(11, 4, 10, 4) + (10..21).flatMap { y -> listOf(8 to y, 9 to y) }
        6 -> rect(14, 2, 4, 8) + rect(12, 4, 8, 3)
        7 -> emptyList()
        8 -> rect(9, 4, 14, 6) + rect(8, 10, 16, 8)
        9 -> rect(10, 5, 5, 6) + rect(11, 4, 8, 2)
        10 -> (10..21).flatMap { x -> (4..12).map { y -> if ((x + y) % 2 == 0) x to y else null } }.filterNotNull()
        11 -> rect(12, 3, 8, 5) + listOf(11 to 8, 20 to 8, 10 to 9, 21 to 9)
        12 -> rect(10, 4, 12, 3) + (10..21).flatMap { x -> listOf(x to 7, x to 8) }
        13 -> rect(9, 5, 14, 2) + listOf(8 to 6, 23 to 6, 8 to 7, 23 to 7)
        else -> rect(11, 4, 10, 5) + rect(10, 9, 12, 2)
    }

    fun bodyPixels(skin: Int): List<Pair<Int, Int>> =
        rect(13, 18, 6, 4) + rect(12, 22, 8, 6) + rect(11, 28, 10, 4) +
            listOf(10 to 20, 21 to 20, 10 to 21, 21 to 21, 9 to 22, 22 to 22)

    fun headPixels(face: Int): List<Pair<Int, Int>> = when (face % 8) {
        1 -> rect(11, 10, 10, 9)
        2 -> rect(10, 10, 12, 9)
        3 -> rect(11, 11, 10, 8)
        4 -> rect(12, 10, 8, 9)
        5 -> rect(11, 10, 10, 8) + listOf(8 to 14, 23 to 14)
        6 -> rect(11, 10, 10, 9) + listOf(8 to 13, 23 to 13, 7 to 14, 24 to 14)
        7 -> rect(10, 10, 12, 8)
        else -> rect(11, 10, 10, 9)
    }

    fun eyePixels(style: Int): List<Pair<Int, Int>> = when (style % 8) {
        1 -> listOf(13 to 14, 14 to 14, 17 to 14, 18 to 14)
        2 -> rect(12, 14, 3, 2) + rect(17, 14, 3, 2)
        4 -> listOf(13 to 13, 14 to 14, 17 to 13, 18 to 14)
        5 -> listOf(13 to 14, 18 to 14)
        6 -> rect(12, 13, 3, 3) + rect(17, 13, 3, 3)
        7 -> emptyList()
        else -> listOf(13 to 14, 14 to 14, 17 to 14, 18 to 14)
    }

    fun mouthPixels(style: Int): List<Pair<Int, Int>> = when (style % 6) {
        0 -> listOf(14 to 17, 15 to 17, 16 to 17, 17 to 17)
        1 -> listOf(14 to 17, 17 to 17)
        2 -> listOf(14 to 17, 15 to 17, 16 to 17, 17 to 17, 18 to 17)
        3 -> listOf(15 to 17, 16 to 17)
        4 -> rect(14, 17, 5, 2)
        else -> listOf(15 to 17, 16 to 17, 17 to 17)
    }

    fun topPixels(style: Int): List<Pair<Int, Int>> = when (style % 14) {
        1 -> rect(11, 20, 10, 2) + rect(12, 22, 8, 4)
        2 -> rect(10, 20, 12, 6) + rect(9, 22, 2, 4) + rect(21, 22, 2, 4)
        3 -> rect(11, 20, 10, 7)
        4 -> rect(10, 20, 12, 3) + rect(11, 23, 10, 4)
        5 -> rect(9, 20, 14, 8)
        6 -> rect(12, 20, 8, 5)
        7 -> rect(11, 20, 10, 6) + rect(10, 21, 1, 3) + rect(21, 21, 1, 3)
        8 -> rect(10, 20, 12, 7) + rect(9, 21, 14, 1)
        9 -> rect(11, 20, 10, 5) + rect(10, 25, 12, 2)
        10 -> rect(11, 20, 10, 8)
        11 -> rect(10, 20, 12, 4) + rect(11, 24, 10, 3)
        12 -> rect(9, 20, 14, 5) + rect(8, 21, 16, 1)
        13 -> rect(10, 19, 12, 8) + rect(9, 20, 14, 1)
        else -> rect(12, 20, 8, 6)
    }

    fun bottomPixels(style: Int): List<Pair<Int, Int>> = when (style % 10) {
        1 -> rect(12, 28, 8, 3) + rect(13, 31, 3, 6) + rect(16, 31, 3, 6)
        2 -> rect(12, 28, 8, 2) + rect(11, 30, 10, 4)
        3 -> rect(11, 28, 10, 3) + rect(12, 31, 4, 5) + rect(16, 31, 4, 5)
        4 -> rect(12, 28, 8, 8)
        5 -> rect(13, 28, 6, 10)
        6 -> rect(11, 28, 10, 10)
        7 -> rect(10, 28, 12, 4) + rect(11, 32, 10, 6)
        8 -> rect(12, 28, 8, 3) + rect(11, 31, 4, 7) + rect(17, 31, 4, 7)
        9 -> rect(12, 28, 8, 9)
        else -> rect(13, 28, 6, 3) + rect(12, 31, 4, 7) + rect(16, 31, 4, 7)
    }

    fun armorPixels(style: Int): List<Pair<Int, Int>> = when (style) {
        -1 -> emptyList()
        0 -> rect(11, 20, 10, 2) + rect(12, 22, 8, 3)
        1 -> rect(10, 19, 12, 8)
        2 -> rect(9, 19, 14, 10) + rect(10, 18, 12, 1)
        3 -> rect(8, 18, 16, 12) + rect(11, 16, 10, 2)
        4 -> rect(10, 19, 12, 9) + rect(11, 17, 10, 2)
        5 -> rect(9, 18, 14, 11) + listOf(10 to 17, 21 to 17, 11 to 16, 20 to 16)
        6 -> rect(10, 20, 12, 7)
        else -> emptyList()
    }

    fun weaponPixels(style: Int): List<Pair<Int, Int>> = when (style) {
        0 -> emptyList()
        1 -> (18..28).flatMap { y -> listOf(22 to y, 23 to y) } + rect(21, 18, 4, 3)
        2 -> (20..27).flatMap { y -> listOf(22 to y) } + listOf(20 to 20, 21 to 20, 22 to 19, 23 to 19)
        3 -> listOf(8 to 20, 8 to 21, 7 to 22, 7 to 23, 6 to 24, 6 to 25) + rect(5, 26, 3, 2)
        4 -> (12..30).flatMap { y -> listOf(23 to y, 24 to y) } + rect(22, 10, 3, 4)
        5 -> (20..26).flatMap { y -> listOf(21 to y, 22 to y) }
        6 -> (18..26).flatMap { y -> listOf(22 to y) } + rect(20, 16, 6, 4)
        7 -> (18..28).flatMap { y -> listOf(22 to y) } + rect(20, 14, 2, 5)
        8 -> rect(7, 20, 3, 8) + rect(6, 22, 5, 4)
        9 -> rect(21, 20, 4, 6)
        else -> emptyList()
    }

    fun accessoryPixels(style: Int): List<Pair<Int, Int>> = when (style) {
        -1 -> emptyList()
        1 -> rect(10, 14, 12, 2) + listOf(9 to 14, 22 to 14)
        2 -> rect(10, 12, 12, 2)
        3 -> (11..20).flatMap { x -> (12..18).map { x to it } }
        5 -> rect(10, 3, 12, 3)
        6 -> rect(11, 2, 10, 2)
        8 -> listOf(8 to 12, 9 to 11, 10 to 10, 22 to 10, 23 to 11, 24 to 12)
        9 -> rect(8, 10, 2, 10)
        10 -> listOf(10 to 19, 11 to 19, 20 to 19, 21 to 19)
        11 -> listOf(15 to 18, 16 to 18)
        else -> emptyList()
    }

    fun bootPixels(): List<Pair<Int, Int>> =
        rect(12, 35, 4, 3) + rect(16, 35, 4, 3) + rect(11, 38, 5, 2) + rect(16, 38, 5, 2)

    private fun rect(x: Int, y: Int, w: Int, h: Int): List<Pair<Int, Int>> =
        (x until x + w).flatMap { px -> (y until y + h).map { py -> px to py } }
}
