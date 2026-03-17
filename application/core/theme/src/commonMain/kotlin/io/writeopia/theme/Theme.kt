package io.writeopia.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import io.github.kdroidfilter.platformtools.darkmodedetector.isSystemInDarkMode

// ─── Light palette (seed: #B7409A, generated via Material Theme Builder) ───

private val md_light_primary = Color(0xFFB7409A)
private val md_light_onPrimary = Color(0xFFFFFFFF)
private val md_light_primaryContainer = Color(0xFFFFD8F0)
private val md_light_onPrimaryContainer = Color(0xFF3D0030)
private val md_light_secondary = Color(0xFF6B5767)
private val md_light_onSecondary = Color(0xFFFFFFFF)
private val md_light_secondaryContainer = Color(0xFFF3DAF0)
private val md_light_onSecondaryContainer = Color(0xFF261527)
private val md_light_tertiary = Color(0xFF815348)
private val md_light_onTertiary = Color(0xFFFFFFFF)
private val md_light_tertiaryContainer = Color(0xFFFFD9CF)
private val md_light_onTertiaryContainer = Color(0xFF321209)
private val md_light_error = Color(0xFFBA1A1A)
private val md_light_onError = Color(0xFFFFFFFF)
private val md_light_errorContainer = Color(0xFFFFDAD6)
private val md_light_onErrorContainer = Color(0xFF410002)
private val md_light_background = Color(0xFFFFF7FB)
private val md_light_onBackground = Color(0xFF1E1A1D)
private val md_light_surface = Color(0xFFFFF7FB)
private val md_light_onSurface = Color(0xFF1E1A1D)
private val md_light_surfaceVariant = Color(0xFFEDD8E8)
private val md_light_onSurfaceVariant = Color(0xFF4C4049)
private val md_light_outline = Color(0xFF7F7083)
private val md_light_outlineVariant = Color(0xFFD0C3CB)
private val md_light_inverseSurface = Color(0xFF332932)
private val md_light_inverseOnSurface = Color(0xFFF8EDF4)
private val md_light_inversePrimary = Color(0xFFF9AADB)

// ─── Dark palette ────────────────────────────────────────────────────────────

private val md_dark_primary = Color(0xFFF9AADB)
private val md_dark_onPrimary = Color(0xFF61074F)
private val md_dark_primaryContainer = Color(0xFF7E2269)
private val md_dark_onPrimaryContainer = Color(0xFFFFD8F0)
private val md_dark_secondary = Color(0xFFD6BECE)
private val md_dark_onSecondary = Color(0xFF3B2A3B)
private val md_dark_secondaryContainer = Color(0xFF533F52)
private val md_dark_onSecondaryContainer = Color(0xFFF3DAF0)
private val md_dark_tertiary = Color(0xFFEFB8A5)
private val md_dark_onTertiary = Color(0xFF4E2317)
private val md_dark_tertiaryContainer = Color(0xFF68392B)
private val md_dark_onTertiaryContainer = Color(0xFFFFD9CF)
private val md_dark_error = Color(0xFFFFB4AB)
private val md_dark_onError = Color(0xFF690005)
private val md_dark_errorContainer = Color(0xFF93000A)
private val md_dark_onErrorContainer = Color(0xFFFFDAD6)
private val md_dark_background = Color(0xFF1E1A1D)
private val md_dark_onBackground = Color(0xFFE9E1E6)
private val md_dark_surface = Color(0xFF1E1A1D)
private val md_dark_onSurface = Color(0xFFE9E1E6)
private val md_dark_surfaceVariant = Color(0xFF4C4049)
private val md_dark_onSurfaceVariant = Color(0xFFD0C3CB)
private val md_dark_outline = Color(0xFF998E9B)
private val md_dark_outlineVariant = Color(0xFF4C4049)
private val md_dark_inverseSurface = Color(0xFFE9E1E6)
private val md_dark_inverseOnSurface = Color(0xFF332932)
private val md_dark_inversePrimary = Color(0xFFB7409A)

// ─── Color schemes ───────────────────────────────────────────────────────────

private val DarkColorPalette = darkColorScheme(
    primary = md_dark_primary,
    onPrimary = md_dark_onPrimary,
    primaryContainer = md_dark_primaryContainer,
    onPrimaryContainer = md_dark_onPrimaryContainer,
    secondary = md_dark_secondary,
    onSecondary = md_dark_onSecondary,
    secondaryContainer = md_dark_secondaryContainer,
    onSecondaryContainer = md_dark_onSecondaryContainer,
    tertiary = md_dark_tertiary,
    onTertiary = md_dark_onTertiary,
    tertiaryContainer = md_dark_tertiaryContainer,
    onTertiaryContainer = md_dark_onTertiaryContainer,
    error = md_dark_error,
    onError = md_dark_onError,
    errorContainer = md_dark_errorContainer,
    onErrorContainer = md_dark_onErrorContainer,
    background = md_dark_background,
    onBackground = md_dark_onBackground,
    surface = md_dark_surface,
    onSurface = md_dark_onSurface,
    surfaceVariant = md_dark_surfaceVariant,
    onSurfaceVariant = md_dark_onSurfaceVariant,
    outline = md_dark_outline,
    outlineVariant = md_dark_outlineVariant,
    inverseSurface = md_dark_inverseSurface,
    inverseOnSurface = md_dark_inverseOnSurface,
    inversePrimary = md_dark_inversePrimary,
    scrim = Color(0xFF000000),
)

private val LightColorPalette = lightColorScheme(
    primary = md_light_primary,
    onPrimary = md_light_onPrimary,
    primaryContainer = md_light_primaryContainer,
    onPrimaryContainer = md_light_onPrimaryContainer,
    secondary = md_light_secondary,
    onSecondary = md_light_onSecondary,
    secondaryContainer = md_light_secondaryContainer,
    onSecondaryContainer = md_light_onSecondaryContainer,
    tertiary = md_light_tertiary,
    onTertiary = md_light_onTertiary,
    tertiaryContainer = md_light_tertiaryContainer,
    onTertiaryContainer = md_light_onTertiaryContainer,
    error = md_light_error,
    onError = md_light_onError,
    errorContainer = md_light_errorContainer,
    onErrorContainer = md_light_onErrorContainer,
    background = md_light_background,
    onBackground = md_light_onBackground,
    surface = md_light_surface,
    onSurface = md_light_onSurface,
    surfaceVariant = md_light_surfaceVariant,
    onSurfaceVariant = md_light_onSurfaceVariant,
    outline = md_light_outline,
    outlineVariant = md_light_outlineVariant,
    inverseSurface = md_light_inverseSurface,
    inverseOnSurface = md_light_inverseOnSurface,
    inversePrimary = md_light_inversePrimary,
    scrim = Color(0xFF000000),
)

// ─── Custom semantic tokens ──────────────────────────────────────────────────

@Immutable
data class WriteopiaColors(
    val globalBackground: Color,
    val optionsSelector: Color,
    val lightBackground: Color,
    // WCAG AA: textLight on globalBackground ≥ 7:1, textLighter ≥ 4.5:1
    val textLight: Color,
    val textLighter: Color,
    val tintLight: Color,
    val highlight: Color,
    val selectedBg: Color,
    val cardBg: Color,
    val cardShadow: Color,
    val cardPlaceHolderBackground: Color,
    val searchBackground: Color,
    val linkColor: Color,
    val dividerColor: Color,
    val defaultButton: Color,
)

val LocalWriteopiaColors = staticCompositionLocalOf {
    WriteopiaColors(
        globalBackground = Color.Unspecified,
        optionsSelector = Color.Unspecified,
        lightBackground = Color.Unspecified,
        textLight = Color.Unspecified,
        textLighter = Color.Unspecified,
        tintLight = Color.Unspecified,
        highlight = Color.Unspecified,
        selectedBg = Color.Unspecified,
        cardBg = Color.Unspecified,
        cardShadow = Color.Unspecified,
        cardPlaceHolderBackground = Color.Unspecified,
        searchBackground = Color.Unspecified,
        linkColor = Color.Unspecified,
        dividerColor = Color.Unspecified,
        defaultButton = Color.Unspecified,
    )
}

@Composable
fun WriteopiaTheme(
    darkTheme: Boolean = isSystemInDarkMode(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColorPalette else LightColorPalette

    // Custom semantic tokens derived from the full M3 scheme
    val writeopiaColors = if (darkTheme) {
        WriteopiaColors(
            globalBackground = md_dark_background,               // #1E1A1D
            lightBackground = md_dark_surfaceVariant,            // #4C4049
            optionsSelector = Color(0x22FFFFFF),
            textLight = md_dark_onSurface,                       // #E9E1E6  – 14.8:1 ✓
            textLighter = md_dark_outline,                       // #998E9B  –  5.3:1 ✓
            tintLight = md_dark_onSurface,                       // #E9E1E6
            highlight = md_dark_surfaceVariant,                  // #4C4049
            selectedBg = md_dark_primaryContainer.copy(alpha = 0.35f),
            cardBg = colorScheme.surfaceContainer,
            cardShadow = Color.Black.copy(alpha = 0.4f),
            cardPlaceHolderBackground = md_dark_surfaceVariant,  // #4C4049
            searchBackground = md_dark_surfaceVariant,           // #4C4049
            linkColor = md_dark_primary,                         // #F9AADB  –  9.5:1 ✓
            dividerColor = md_dark_outlineVariant,               // #4C4049
            defaultButton = md_dark_primary,                     // #F9AADB
        )
    } else {
        WriteopiaColors(
            globalBackground = md_light_background,              // #FFF7FB
            lightBackground = md_light_surfaceVariant,           // #EDD8E8
            optionsSelector = Color(0x15000000),
            textLight = md_light_onSurface,                      // #1E1A1D  – 18.4:1 ✓
            textLighter = md_light_onSurfaceVariant,             // #4C4049  –  9.5:1 ✓
            tintLight = md_light_onSurface,                      // #1E1A1D
            highlight = md_light_surfaceVariant,                 // #EDD8E8
            selectedBg = md_light_primaryContainer.copy(alpha = 0.5f),
            cardBg = colorScheme.surfaceContainer,
            cardShadow = md_light_outlineVariant,                // #D0C3CB
            cardPlaceHolderBackground = md_light_surfaceVariant, // #EDD8E8
            searchBackground = md_light_surface,                 // #FFF7FB
            linkColor = md_light_primary,                        // #B7409A  –  4.9:1 ✓
            dividerColor = md_light_outlineVariant,              // #D0C3CB
            defaultButton = md_light_primary,                    // #B7409A
        )
    }

    CompositionLocalProvider(LocalWriteopiaColors provides writeopiaColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            shapes = Shapes,
            content = content,
        )
    }
}

object WriteopiaTheme {
    val colorScheme: WriteopiaColors
        @Composable
        get() = LocalWriteopiaColors.current
}
