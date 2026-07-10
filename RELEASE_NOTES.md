# Chronicles — Release Notes

## v1.1.0 (2026-07-10)

**Chronicles** — текстовая MMORPG для Android (UI preview). Kotlin, Jetpack Compose.

### Установка

```bash
# Debug (для разработки / Xiaomi с «Install via USB»)
adb install app/build/outputs/apk/debug/app-debug.apk

# Release (оптимизированная сборка)
adb install app/build/outputs/apk/release/app-release-unsigned.apk
```

> Release APK подписан debug-ключом Gradle по умолчанию. Для публикации в Store нужен собственный keystore.

### Что нового

| Область | Описание |
|--------|----------|
| **Герой** | Редактор облика на LPC-текстурах: кожа, волосы, одежда, броня, оружие, шляпы |
| **Темы** | 12 палитр + отдельный выбор цвета текста |
| **Истории** | Карточки с превью, создание своей истории или вход в «Живой мир» |
| **Навигация** | Прозрачное нижнее меню, плавная анимация, скрытие двойным tap |
| **Персонаж** | 12 рас, 12 классов, кастомные раса/класс, статы, предыстория |

### Системные требования

- Android 7.0+ (API 24)
- Target SDK 36
- Разрешения: интернет, галерея (превью историй), аудио (запланировано)

### Известные ограничения

- Backend (WebSocket/HTTP) ещё не подключён — данные mock
- Release-сборка без R8/minify (optimization disabled)
- На HyperOS/Xiaomi при установке через USB может потребоваться «Install via USB» в настройках разработчика

### Ассеты

Спрайты персонажей: [Universal LPC Spritesheet Character Generator](https://github.com/sanderfrenken/Universal-LPC-Spritesheet-Character-Generator) (CC-BY-SA / GPL). См. `app/src/main/assets/lpc/CREDITS.txt`.

### Сборка из исходников

```bash
./gradlew assembleDebug    # debug APK
./gradlew assembleRelease  # release APK
```

Версия: см. файл `VERSION` и `app/build.gradle.kts` (`versionName` / `versionCode`).
