package com.example.testtextmmo.ui.screens.character

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.testtextmmo.data.AppState
import com.example.testtextmmo.data.model.CharacterAppearance
import com.example.testtextmmo.data.model.CharacterClass
import com.example.testtextmmo.data.model.CharacterRace
import com.example.testtextmmo.data.model.CharacterStats
import com.example.testtextmmo.data.model.GameCharacter
import com.example.testtextmmo.ui.components.GlassCard
import com.example.testtextmmo.ui.components.GradientButton
import com.example.testtextmmo.ui.components.MmoFilledButton
import com.example.testtextmmo.ui.components.MmoOutlinedButton
import com.example.testtextmmo.ui.components.PixelCharacterCreator
import com.example.testtextmmo.ui.components.PixelCharacterView
import com.example.testtextmmo.ui.components.SectionHeader
import com.example.testtextmmo.ui.components.StatPill
import com.example.testtextmmo.ui.components.StepIndicator
import com.example.testtextmmo.ui.components.mmoTextFieldColors
import com.example.testtextmmo.ui.theme.MysticCyan
import java.util.UUID

private const val TOTAL_STEPS = 5

@Composable
fun CharacterScreen(appState: AppState, onCharacterSaved: () -> Unit = {}) {
    val character = appState.currentCharacter
    var editing by rememberSaveable { mutableStateOf(character == null) }

    if (character != null && !editing) {
        HeroProfileView(
            character = character,
            onEdit = { editing = true }
        )
    } else {
        CharacterCreationWizard(
            appState = appState,
            existing = character,
            onSaved = {
                editing = false
                onCharacterSaved()
            },
            onCancel = { if (character != null) editing = false }
        )
    }
}

@Composable
private fun HeroProfileView(character: GameCharacter, onEdit: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SectionHeader(
            title = character.name,
            subtitle = "${character.race} · ${character.characterClass}"
        )
        Spacer(modifier = Modifier.height(16.dp))
        PixelCharacterView(
            appearance = character.appearance,
            modifier = Modifier.fillMaxWidth().height(280.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            StatPill("СИЛ", "${character.stats.strength}", Modifier.weight(1f))
            StatPill("ЛОВ", "${character.stats.agility}", Modifier.weight(1f))
            StatPill("ИНТ", "${character.stats.intelligence}", Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            StatPill("ХАР", "${character.stats.charisma}", Modifier.weight(1f))
            StatPill("ВЫН", "${character.stats.endurance}", Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(16.dp))
        GlassCard(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Предыстория", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                Text(
                    character.backstory,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        GradientButton(text = "Редактировать героя", onClick = onEdit, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun CharacterCreationWizard(
    appState: AppState,
    existing: GameCharacter?,
    onSaved: () -> Unit,
    onCancel: () -> Unit
) {
    var step by rememberSaveable { mutableIntStateOf(0) }
    var name by rememberSaveable { mutableStateOf(existing?.name ?: "") }
    var backstory by rememberSaveable { mutableStateOf(existing?.backstory ?: "") }
    var selectedRaceId by rememberSaveable { mutableStateOf(resolveRaceId(appState, existing?.race)) }
    var selectedClassId by rememberSaveable { mutableStateOf(resolveClassId(appState, existing?.characterClass)) }
    var strength by rememberSaveable { mutableIntStateOf(existing?.stats?.strength ?: 10) }
    var agility by rememberSaveable { mutableIntStateOf(existing?.stats?.agility ?: 10) }
    var intelligence by rememberSaveable { mutableIntStateOf(existing?.stats?.intelligence ?: 10) }
    var charisma by rememberSaveable { mutableIntStateOf(existing?.stats?.charisma ?: 10) }
    var endurance by rememberSaveable { mutableIntStateOf(existing?.stats?.endurance ?: 10) }

    var appearance by remember { mutableStateOf(existing?.appearance ?: CharacterAppearance()) }

    var customRaceName by rememberSaveable { mutableStateOf("") }
    var customClassName by rememberSaveable { mutableStateOf("") }

    val races = appState.allRaces()
    val classes = appState.allClasses()
    val selectedRace = races.firstOrNull { it.id == selectedRaceId } ?: races.first()
    val selectedClass = classes.firstOrNull { it.id == selectedClassId } ?: classes.first()

    val canProceed = when (step) {
        0 -> name.trim().length >= 2
        1, 2 -> true
        3 -> backstory.trim().length >= 10
        4 -> true
        else -> false
    }

    if (step == 0) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SectionHeader(
                title = if (existing != null) "Редактирование" else "Создание героя",
                subtitle = "Шаг 1 из $TOTAL_STEPS · ${stepTitle(0)}",
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            StepIndicator(currentStep = 0, totalSteps = TOTAL_STEPS)
            Spacer(modifier = Modifier.height(20.dp))
            NameStep(name, { name = it })
            Spacer(modifier = Modifier.height(24.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                if (existing != null) {
                    MmoOutlinedButton("Отмена", onCancel, Modifier.fillMaxWidth())
                }
                MmoFilledButton(
                    text = "Далее",
                    onClick = { step++ },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = canProceed
                )
            }
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        SectionHeader(
            title = if (existing != null) "Редактирование" else "Создание героя",
            subtitle = "Шаг ${step + 1} из $TOTAL_STEPS · ${stepTitle(step)}"
        )
        Spacer(modifier = Modifier.height(16.dp))
        StepIndicator(currentStep = step, totalSteps = TOTAL_STEPS)
        Spacer(modifier = Modifier.height(16.dp))

        when (step) {
            1 -> PixelCharacterCreator(appearance, onChange = { appearance = it })
            2 -> OriginStep(
                appState, races, classes, selectedRace, selectedClass,
                selectedRaceId, selectedClassId,
                customRaceName, { customRaceName = it },
                customClassName, { customClassName = it },
                onRaceSelected = { selectedRaceId = it.id },
                onClassSelected = { selectedClassId = it.id }
            )
            3 -> BackstoryStep(backstory, { backstory = it }, selectedRace, selectedClass)
            4 -> StatsStep(strength, agility, intelligence, charisma, endurance,
                { strength = it }, { agility = it }, { intelligence = it },
                { charisma = it }, { endurance = it })
        }

        Spacer(modifier = Modifier.height(24.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            MmoOutlinedButton("Назад", { step-- }, Modifier.fillMaxWidth())
            if (step < TOTAL_STEPS - 1) {
                MmoFilledButton("Далее", { step++ }, Modifier.fillMaxWidth(), canProceed)
            } else {
                MmoFilledButton(
                    text = if (existing != null) "Сохранить" else "Воплотить героя",
                    onClick = {
                        appState.setCharacter(
                            GameCharacter(
                                id = existing?.id ?: UUID.randomUUID().toString(),
                                name = name.trim(),
                                race = selectedRace.name,
                                characterClass = selectedClass.name,
                                backstory = backstory.trim(),
                                stats = CharacterStats(strength, agility, intelligence, charisma, endurance),
                                appearance = appearance
                            )
                        )
                        onSaved()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = canProceed
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun NameStep(name: String, onNameChange: (String) -> Unit) {
    GlassCard(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(20.dp)) {
            Text("Имя героя", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(
                value = name, onValueChange = onNameChange,
                placeholder = { Text("Введите имя") },
                modifier = Modifier.fillMaxWidth(), singleLine = true,
                shape = RoundedCornerShape(14.dp), colors = mmoTextFieldColors()
            )
        }
    }
}

private fun stepTitle(step: Int) = when (step) {
    0 -> "Имя"
    1 -> "Облик"
    2 -> "Раса и класс"
    3 -> "Предыстория"
    4 -> "Характеристики"
    else -> ""
}

private fun resolveRaceId(appState: AppState, name: String?) =
    appState.allRaces().firstOrNull { it.name == name }?.id ?: "human"

private fun resolveClassId(appState: AppState, name: String?) =
    appState.allClasses().firstOrNull { it.name == name }?.id ?: "warrior"

@Composable
private fun OriginStep(
    appState: AppState,
    races: List<CharacterRace>,
    classes: List<CharacterClass>,
    selectedRace: CharacterRace,
    selectedClass: CharacterClass,
    selectedRaceId: String,
    selectedClassId: String,
    customRaceName: String,
    onCustomRaceChange: (String) -> Unit,
    customClassName: String,
    onCustomClassChange: (String) -> Unit,
    onRaceSelected: (CharacterRace) -> Unit,
    onClassSelected: (CharacterClass) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SelectionList("Раса", races, selectedRaceId, onRaceSelected)
        CustomEntryRow("Своя раса", customRaceName, onCustomRaceChange) {
            appState.addCustomRace(customRaceName, "Авторская раса", "Особое")
            appState.customRaces.lastOrNull()?.let(onRaceSelected)
        }
        SelectionList("Класс", classes, selectedClassId, onClassSelected)
        CustomEntryRow("Свой класс", customClassName, onCustomClassChange) {
            appState.addCustomClass(customClassName, "Авторский класс", "Уникальный")
            appState.customClasses.lastOrNull()?.let(onClassSelected)
        }
    }
}

@Composable
private fun <T> SelectionList(
    title: String,
    items: List<T>,
    selectedId: String,
    onSelect: (T) -> Unit,
    id: (T) -> String = { when (it) {
        is CharacterRace -> it.id
        is CharacterClass -> it.id
        else -> ""
    }},
    label: (T) -> String = { when (it) {
        is CharacterRace -> "${it.emoji} ${it.name}"
        is CharacterClass -> "${it.emoji} ${it.name}"
        else -> ""
    }},
    badge: (T) -> String = { when (it) {
        is CharacterRace -> it.bonusStat
        is CharacterClass -> it.primaryStat
        else -> ""
    }}
) {
    Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
    items.forEach { item ->
        val selected = id(item) == selectedId
        val shape = RoundedCornerShape(14.dp)
        val borderColor = if (selected) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.outline
        }
        val containerColor = if (selected) {
            MaterialTheme.colorScheme.primary.copy(alpha = 0.22f)
        } else {
            MaterialTheme.colorScheme.surface.copy(alpha = 0.88f)
        }
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 3.dp)
                .clip(shape)
                .border(BorderStroke(if (selected) 2.dp else 1.dp, borderColor), shape)
                .clickable { onSelect(item) },
            color = containerColor,
            shape = shape
        ) {
            Row(
                Modifier.padding(horizontal = 14.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = label(item),
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
                    color = if (selected) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSurface
                    }
                )
                Text(
                    text = badge(item),
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                    color = if (selected) MaterialTheme.colorScheme.primary else MysticCyan
                )
            }
        }
    }
}

@Composable
private fun CustomEntryRow(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    onAdd: () -> Unit
) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
        OutlinedTextField(
            value = value, onValueChange = onValueChange,
            label = { Text(label) },
            modifier = Modifier.weight(1f),
            singleLine = true, shape = RoundedCornerShape(12.dp),
            colors = mmoTextFieldColors()
        )
        MmoFilledButton("Добавить", onAdd, Modifier.weight(1f), value.isNotBlank())
    }
}

@Composable
private fun BackstoryStep(
    backstory: String, onChange: (String) -> Unit,
    race: CharacterRace, cls: CharacterClass
) {
    GlassCard(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(20.dp)) {
            Text("Предыстория", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Text("${race.emoji} ${race.name} · ${cls.emoji} ${cls.name}",
                style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(vertical = 6.dp))
            OutlinedTextField(
                value = backstory, onValueChange = onChange,
                modifier = Modifier.fillMaxWidth().height(140.dp),
                placeholder = { Text("История вашего героя...") },
                shape = RoundedCornerShape(14.dp), colors = mmoTextFieldColors()
            )
        }
    }
}

@Composable
private fun StatsStep(
    str: Int, agi: Int, int: Int, cha: Int, end: Int,
    onStr: (Int) -> Unit, onAgi: (Int) -> Unit, onInt: (Int) -> Unit,
    onCha: (Int) -> Unit, onEnd: (Int) -> Unit
) {
    GlassCard(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            StatSlider("Сила", str, onStr)
            StatSlider("Ловкость", agi, onAgi)
            StatSlider("Интеллект", int, onInt)
            StatSlider("Харизма", cha, onCha)
            StatSlider("Выносливость", end, onEnd)
        }
    }
}

@Composable
private fun StatSlider(label: String, value: Int, onChange: (Int) -> Unit) {
    Column(Modifier.padding(vertical = 4.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(label)
            Text("$value", color = MysticCyan, fontWeight = FontWeight.Bold)
        }
        Slider(
            value = value.toFloat(), onValueChange = { onChange(it.toInt()) },
            valueRange = 5f..20f, steps = 14,
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.primary,
                activeTrackColor = MaterialTheme.colorScheme.secondary
            )
        )
    }
}
