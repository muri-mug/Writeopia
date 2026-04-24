# Writeopia — Claude Code Instructions

## Project
Kotlin Multiplatform (KMP) app using Compose Multiplatform.
Targets: Android · iOS · Desktop (JVM) · Web (JS + WASM).

---

## Design System — Distopia

The design system is **Material Design 3 baseline**. Public docs: https://muri-mug.github.io/distopia/

### Where to edit each token

| What to change | File |
|---|---|
| Color palette (light + dark) | `application/core/theme/src/commonMain/kotlin/io/writeopia/theme/Theme.kt` |
| Semantic color tokens (`WriteopiaColors`) | Same file — `WriteopiaColors` data class + `writeopiaColors` blocks |
| App-specific extra colors | `application/core/theme/src/commonMain/kotlin/io/writeopia/theme/Color.kt` |
| Shape scale (corner radii) | `application/core/theme/src/commonMain/kotlin/io/writeopia/theme/Shape.kt` |
| Typography (font sizes, weights, line heights) | `application/core/theme/src/commonMain/kotlin/io/writeopia/theme/Type.kt` |
| Button variants | `application/core/common_ui/src/commonMain/kotlin/io/writeopia/commonui/buttons/WButton.kt` |

### How to edit colors

Colors in `Theme.kt` follow the pattern:
```
private val md_light_<role>  = Color(0xFFHHHHHH)   ← light value
private val md_dark_<role>   = Color(0xFFHHHHHH)   ← dark value
```

Roles: `primary`, `onPrimary`, `primaryContainer`, `onPrimaryContainer`,
`secondary`, `onSecondary`, `secondaryContainer`, `onSecondaryContainer`,
`tertiary`, `onTertiary`, `tertiaryContainer`, `onTertiaryContainer`,
`error`, `onError`, `errorContainer`, `onErrorContainer`,
`background`, `onBackground`, `surface`, `onSurface`,
`surfaceVariant`, `onSurfaceVariant`, `outline`, `outlineVariant`,
`inverseSurface`, `inverseOnSurface`, `inversePrimary`.

After changing a raw color value, the `LightColorPalette` / `DarkColorPalette`
and `WriteopiaColors` blocks in the same file pick it up automatically.

### How to edit semantic tokens

`WriteopiaColors` maps to specific UI meanings. Edit the `if (darkTheme)` block:
```kotlin
WriteopiaColors(
    globalBackground = ...,    // app canvas
    lightBackground  = ...,    // sidebar / panel bg
    textLight        = ...,    // primary text
    textLighter      = ...,    // subdued text
    highlight        = ...,    // hover surface
    selectedBg       = ...,    // selected list item
    dividerColor     = ...,    // dividers
    linkColor        = ...,    // hyperlinks
    defaultButton    = ...,    // fallback button color
    cardBg           = ...,    // note card
    searchBackground = ...,    // search input bg
    ...
)
```

### How to edit shape scale

`Shape.kt` — change any `RoundedCornerShape(Xdp)` value:
```kotlin
extraSmall = RoundedCornerShape(4.dp)   // inputs, chips
small      = RoundedCornerShape(8.dp)   // cards, dialogs
medium     = RoundedCornerShape(12.dp)  // sheets
large      = RoundedCornerShape(16.dp)  // nav drawers
extraLarge = RoundedCornerShape(28.dp)  // FAB
```

### How to edit typography

`Type.kt` — each style is a `TextStyle`. Properties: `fontSize`, `lineHeight`,
`letterSpacing`, `fontWeight`. Font is `FontFamily.Default` (Roboto on Android).

### How to add a new WButton variant

1. Add the variant to `WButtonVariant` enum in `WButton.kt`
2. Add the corresponding `when` branch calling the appropriate M3 composable
3. Update the `design-system.html` demo and distopia `README.md`

### After any token/component change

Update the documentation page:
- `design-system.html` (root of this repo) — HTML preview
- `distopia/index.html` — published GitHub Pages doc

---

## Module structure

```
application/
  core/
    theme/          ← Distopia design tokens (colors, shape, type)
    common_ui/      ← Shared composables (WButton, etc.)
    common_ui_tests/← Robot pattern + shared E2E test scenarios
    auth_core/
    analytics/
    ...
  features/
    global_shell/   ← App shell, sidebar (SideGlobalMenu.kt)
    editor/         ← Note editor
    note_menu/      ← Note list / folder browser
    search/         ← Search feature
    auth/
    account/
    ...
  composeApp/       ← Android + Desktop entry points
  web/              ← Web entry point
```

## DI pattern

No Koin/Hilt. Each service uses:
```kotlin
expect class FooInjection {
    fun provide(): Foo
    companion object { fun singleton(): FooInjection }
}
```
Actuals in: `androidMain`, `jvmMain`, `nativeMain`, `webMain`.

## Running tests

```bash
# Unit tests (JVM)
JAVA_HOME=/Library/Java/JavaVirtualMachines/temurin-21.jdk/Contents/Home \
  ./gradlew :application:features:search:jvmTest
  ./gradlew :application:features:note_menu:jvmTest

# Compose UI / E2E (Desktop JVM)
./gradlew :application:composeApp:jvmTest

# Android instrumented
./gradlew :application:composeApp:connectedAndroidTest

# All JVM tests
./gradlew jvmTest
```

## Test patterns

- **Unit tests**: `jvmTest` source set, MockK + kotlinx-coroutines-test
- **Compose UI**: Robot pattern via `common_ui_tests` module
- **Robots**: `DocumentsMenuRobot`, `DocumentEditRobot`, `SidebarRobot`
- **Shared scenarios**: `EditorCommonTests`, `NavigationCommonTests`

## Build

```bash
JAVA_HOME=/Library/Java/JavaVirtualMachines/temurin-21.jdk/Contents/Home \
  ./gradlew :application:composeApp:compileKotlinJvm
```

## Sidebar testTags

| Tag | Element |
|---|---|
| `sideMenuToggle` | Collapse/expand arrow |
| `sideMenuSearch` | Search bar |
| `sideMenuHome` | Home nav item |
| `sideMenuFavorites` | Favorites nav item |
| `sideMenuSettings` | Settings nav item |
| `sideMenuHelp` | Help action |
| `sideMenuLogout` | Logout action |
