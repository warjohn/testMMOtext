package com.example.testtextmmo.data

import com.example.testtextmmo.data.model.CharacterAppearance

data class LpcAsset(val path: String, val label: String)

/** Universal LPC spritesheet character assets (CC-BY-SA / GPL). See assets/lpc/CREDITS.txt */
object LpcAssetCatalog {
    const val FRAME = 64
    const val IDLE_ROW = 10
    const val IDLE_COL = 0

    val bodies: List<LpcAsset> = listOf(
        LpcAsset("body/male_amber.png", "Янтарная"),
        LpcAsset("body/male_black.png", "Тёмная"),
        LpcAsset("body/male_blue.png", "Синяя"),
        LpcAsset("body/male_bronze.png", "Бронза"),
        LpcAsset("body/male_brown.png", "Коричневая"),
        LpcAsset("body/male_green.png", "Зелёная"),
        LpcAsset("body/male_lavender.png", "Лиловая"),
        LpcAsset("body/male_light.png", "Светлая"),
        LpcAsset("body/male_olive.png", "Оливковая"),
        LpcAsset("body/male_taupe.png", "Бежевая"),
        LpcAsset("body/male_zombie_green.png", "Зомби"),
    )

    val hair: List<LpcAsset> = listOf(
        LpcAsset("hair/hair_afro_female_carrot.png", "Афро · рыжие"),
        LpcAsset("hair/hair_afro_female_dark_brown.png", "Афро · каштан"),
        LpcAsset("hair/hair_afro_female_redhead.png", "Афро · медные"),
        LpcAsset("hair/hair_balding_adult.png", "Лысина"),
        LpcAsset("hair/hair_balding_adult_dark_brown.png", "Лысина · каштан"),
        LpcAsset("hair/hair_balding_adult_redhead.png", "Лысина · рыжие"),
        LpcAsset("hair/hair_bangs_female_carrot.png", "Чёлка · рыжие"),
        LpcAsset("hair/hair_bangs_female_dark_brown.png", "Чёлка · каштан"),
        LpcAsset("hair/hair_bangs_female_redhead.png", "Чёлка · медные"),
        LpcAsset("hair/hair_bedhead_female_carrot.png", "Тусовка · рыжие"),
        LpcAsset("hair/hair_bedhead_female_dark_brown.png", "Тусовка · каштан"),
        LpcAsset("hair/hair_bedhead_female_redhead.png", "Тусовка · медные"),
        LpcAsset("hair/hair_bob_adult.png", "Боб"),
        LpcAsset("hair/hair_bob_adult_dark_brown.png", "Боб · каштан"),
        LpcAsset("hair/hair_bob_adult_redhead.png", "Боб · рыжие"),
        LpcAsset("hair/hair_braid_child_lightblonde.png", "Коса · светлые"),
        LpcAsset("hair/hair_braid_child_raven_2.png", "Коса · чёрные"),
        LpcAsset("hair/hair_buzzcut_adult.png", "Ёжик"),
        LpcAsset("hair/hair_buzzcut_adult_dark_brown.png", "Ёжик · каштан"),
        LpcAsset("hair/hair_buzzcut_adult_redhead.png", "Ёжик · рыжие"),
        LpcAsset("hair/hair_curly_short_adult.png", "Кудри"),
        LpcAsset("hair/hair_curly_short_adult_dark_brown.png", "Кудри · каштан"),
        LpcAsset("hair/hair_curly_short_adult_redhead.png", "Кудри · рыжие"),
        LpcAsset("hair/hair_long_female_carrot.png", "Длинные · рыжие"),
        LpcAsset("hair/hair_long_female_dark_brown.png", "Длинные · каштан"),
        LpcAsset("hair/hair_long_female_redhead.png", "Длинные · медные"),
        LpcAsset("hair/hair_messy_child_lightblonde.png", "Растрёп · светлые"),
        LpcAsset("hair/hair_messy_child_raven_2.png", "Растрёп · чёрные"),
        LpcAsset("hair/hair_ponytail_female_carrot.png", "Хвост · рыжие"),
        LpcAsset("hair/hair_ponytail_female_dark_brown.png", "Хвост · каштан"),
        LpcAsset("hair/hair_ponytail_female_redhead.png", "Хвост · медные"),
    )

    val torso: List<LpcAsset> = listOf(
        LpcAsset("torso/torso_clothes_longsleeve_formal_male_white.png", "Формальная белая"),
        LpcAsset("torso/torso_clothes_longsleeve_formal_striped_male_white.png", "Формальная полоска"),
        LpcAsset("torso/torso_clothes_longsleeve_longsleeve_male_blue.png", "Синяя"),
        LpcAsset("torso/torso_clothes_longsleeve_longsleeve_male_bluegray.png", "Серо-синяя"),
        LpcAsset("torso/torso_clothes_longsleeve_longsleeve_male_charcoal.png", "Угольная"),
        LpcAsset("torso/torso_clothes_longsleeve_longsleeve_male_forest.png", "Лесная"),
        LpcAsset("torso/torso_clothes_longsleeve_longsleeve_male_gray.png", "Серая"),
        LpcAsset("torso/torso_clothes_longsleeve_longsleeve_male_lavender.png", "Лиловая"),
        LpcAsset("torso/torso_clothes_longsleeve_longsleeve_male_leather.png", "Кожаная"),
        LpcAsset("torso/torso_clothes_longsleeve_longsleeve_male_maroon.png", "Бордовая"),
        LpcAsset("torso/torso_clothes_longsleeve_longsleeve_male_orange.png", "Оранжевая"),
        LpcAsset("torso/torso_clothes_longsleeve_longsleeve_male_pink.png", "Розовая"),
        LpcAsset("torso/torso_clothes_longsleeve_longsleeve_male_purple.png", "Фиолетовая"),
        LpcAsset("torso/torso_clothes_longsleeve_longsleeve_male_red.png", "Красная"),
        LpcAsset("torso/torso_clothes_longsleeve_longsleeve_male_sky.png", "Небесная"),
        LpcAsset("torso/torso_clothes_longsleeve_longsleeve_male_slate.png", "Сланцевая"),
        LpcAsset("torso/torso_clothes_longsleeve_longsleeve_male_tan.png", "Песочная"),
        LpcAsset("torso/torso_clothes_longsleeve_longsleeve_male_teal.png", "Бирюзовая"),
        LpcAsset("torso/torso_clothes_longsleeve_longsleeve_male_white.png", "Белая"),
        LpcAsset("torso/torso_clothes_longsleeve_longsleeve_male_yellow.png", "Жёлтая"),
    )

    val legs: List<LpcAsset> = listOf(
        LpcAsset("legs/legs_pants_male_black.png", "Чёрные"),
        LpcAsset("legs/legs_pants_male_blue.png", "Синие"),
        LpcAsset("legs/legs_pants_male_bluegray.png", "Серо-синие"),
        LpcAsset("legs/legs_pants_male_charcoal.png", "Угольные"),
        LpcAsset("legs/legs_pants_male_forest.png", "Лесные"),
        LpcAsset("legs/legs_pants_male_gray.png", "Серые"),
        LpcAsset("legs/legs_pants_male_lavender.png", "Лиловые"),
        LpcAsset("legs/legs_pants_male_leather.png", "Кожаные"),
        LpcAsset("legs/legs_pants_male_maroon.png", "Бордовые"),
        LpcAsset("legs/legs_pants_male_orange.png", "Оранжевые"),
        LpcAsset("legs/legs_pants_male_pink.png", "Розовые"),
        LpcAsset("legs/legs_pants_male_purple.png", "Фиолетовые"),
        LpcAsset("legs/legs_pants_male_red.png", "Красные"),
        LpcAsset("legs/legs_pants_male_sky.png", "Небесные"),
        LpcAsset("legs/legs_pants_male_slate.png", "Сланцевые"),
        LpcAsset("legs/legs_pants_male_tan.png", "Песочные"),
        LpcAsset("legs/legs_pants_male_teal.png", "Бирюзовые"),
        LpcAsset("legs/legs_pants_male_walnut.png", "Ореховые"),
        LpcAsset("legs/legs_pants_male_white.png", "Белые"),
        LpcAsset("legs/legs_pants_male_yellow.png", "Жёлтые"),
    )

    val feet: List<LpcAsset> = listOf(
        LpcAsset("feet/feet_feet_boots_female_gray.png", "Сапоги серые"),
        LpcAsset("feet/feet_feet_boots_female_charcoal.png", "Сапоги уголь"),
        LpcAsset("feet/feet_feet_boots_female_forest.png", "Сапоги лес"),
        LpcAsset("feet/feet_feet_boots_female_maroon.png", "Сапоги бордо"),
        LpcAsset("feet/feet_hoofs_male_hoofs.png", "Копыта"),
        LpcAsset("feet/feet_hoofs_male_fur_brown.png", "Мех коричн."),
        LpcAsset("feet/feet_hoofs_male_fur_black.png", "Мех чёрный"),
        LpcAsset("feet/feet_hoofs_male_fur_tan.png", "Мех песочный"),
    )

    val armor: List<LpcAsset?> = listOf(
        null,
        LpcAsset("armor/torso_armour_plate_female_steel.png", "Сталь"),
        LpcAsset("armor/torso_armour_plate_female_iron.png", "Железо"),
        LpcAsset("armor/torso_armour_plate_female_silver.png", "Серебро"),
        LpcAsset("armor/torso_armour_plate_female_copper.png", "Медь"),
        LpcAsset("armor/torso_armour_plate_female_ceramic.png", "Керамика"),
        LpcAsset("armor/torso_armour_plate_teen_steel.png", "Латы сталь"),
        LpcAsset("armor/torso_armour_plate_teen_gold.png", "Латы золото"),
        LpcAsset("armor/torso_armour_plate_teen_bronze.png", "Латы бронза"),
        LpcAsset("armor/torso_armour_plate_teen_brass.png", "Латы латунь"),
    )

    val weapons: List<LpcAsset?> = listOf(
        null,
        LpcAsset("weapon/weapon_sword_katana_walk_katana.png", "Катана"),
        LpcAsset("weapon/weapon_sword_saber_saber.png", "Сабля"),
        LpcAsset("weapon/weapon_sword_longsword_alt_walk_longsword_alt.png", "Длинный меч"),
        LpcAsset("weapon/weapon_sword_scimitar_walk_scimitar.png", "Scimitar"),
        LpcAsset("weapon/weapon_sword_glowsword_blue.png", "Магический синий"),
        LpcAsset("weapon/weapon_sword_glowsword_red.png", "Магический красный"),
    )

    val hats: List<LpcAsset?> = listOf(
        null,
        LpcAsset("hat/hat_formal_bowler_adult.png", "Котелок"),
        LpcAsset("hat/hat_formal_bowler_adult_base_brown.png", "Котелок коричн."),
        LpcAsset("hat/hat_formal_bowler_adult_charcoal.png", "Котелок уголь"),
        LpcAsset("hat/hat_formal_bowler_adult_leather.png", "Котелок кожа"),
        LpcAsset("hat/hat_formal_bowler_adult_red.png", "Котелок красный"),
        LpcAsset("hat/hat_formal_bowler_adult_teal.png", "Котелок бирюза"),
    )

    fun resolveLayers(appearance: CharacterAppearance): List<String> {
        val layers = mutableListOf<String>()
        val bodyIdx = appearance.faceType.coerceIn(0, bodies.lastIndex)
        layers += bodies[bodyIdx].path
        val legIdx = appearance.bottomWear.coerceIn(0, legs.lastIndex)
        layers += legs[legIdx].path
        if (feet.isNotEmpty()) {
            layers += feet[appearance.bottomWear.coerceIn(0, feet.lastIndex)].path
        }
        val topIdx = appearance.topWear.coerceIn(0, torso.lastIndex)
        layers += torso[topIdx].path
        if (appearance.armorStyle >= 0) {
            val ai = (appearance.armorStyle + 1).coerceIn(1, armor.lastIndex)
            armor[ai]?.let { layers += it.path }
        }
        val hairIdx = appearance.hairStyle.coerceIn(0, hair.lastIndex)
        layers += hair[hairIdx].path
        if (appearance.accessoryStyle >= 0) {
            val hi = (appearance.accessoryStyle + 1).coerceIn(1, hats.lastIndex)
            hats[hi]?.let { layers += it.path }
        }
        if (appearance.weaponStyle >= 0) {
            val wi = (appearance.weaponStyle + 1).coerceIn(1, weapons.lastIndex)
            weapons[wi]?.let { layers += it.path }
        }
        return layers
    }
}
