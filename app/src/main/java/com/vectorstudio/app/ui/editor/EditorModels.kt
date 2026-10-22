package com.vectorstudio.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColors = darkColorScheme(
    primary = VectorStudioPalette.primary,
    secondary = VectorStudioPalette.secondary,
    tertiary = VectorStudioPalette.tertiary,
    background = VectorStudioPalette.background,
    surface = VectorStudioPalette.surface,
    onPrimary = VectorStudioPalette.onPrimary,
    onSurface = VectorStudioPalette.onSurface,
    onBackground = VectorStudioPalette.onBackground,
    outline = VectorStudioPalette.outline,
    surfaceVariant = VectorStudioPalette.surfaceVariant,
)

private val LightColors = lightColorScheme(
    primary = VectorStudioPalette.primaryLight,
    secondary = VectorStudioPalette.secondaryLight,
    tertiary = VectorStudioPalette.tertiaryLight,
    background = VectorStudioPalette.backgroundLight,
    surface = VectorStudioPalette.surfaceLight,
    onPrimary = VectorStudioPalette.onPrimaryLight,
    onSurface = VectorStudioPalette.onSurfaceLight,
    onBackground = VectorStudioPalette.onBackgroundLight,
    outline = VectorStudioPalette.outlineLight,
    surfaceVariant = VectorStudioPalette.surfaceVariantLight,
)

@Composable
fun VectorStudioTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = if (darkTheme) DarkColors else LightColors
    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content,
    )
}

object VectorStudioPalette {
    val primary = androidx.compose.ui.graphics.Color(0xFF7C9BFF)
    val secondary = androidx.compose.ui.graphics.Color(0xFF6EE7B7)
    val tertiary = androidx.compose.ui.graphics.Color(0xFFF9A8D4)
    val background = androidx.compose.ui.graphics.Color(0xFF0B1020)
    val surface = androidx.compose.ui.graphics.Color(0xFF111827)
    val onPrimary = androidx.compose.ui.graphics.Color(0xFF08111B)
    val onSurface = androidx.compose.ui.graphics.Color(0xFFE5E7EB)
    val onBackground = androidx.compose.ui.graphics.Color(0xFFF3F4F6)
    val outline = androidx.compose.ui.graphics.Color(0xFF374151)
    val surfaceVariant = androidx.compose.ui.graphics.Color(0xFF1F2937)

    val primaryLight = androidx.compose.ui.graphics.Color(0xFF3757D6)
    val secondaryLight = androidx.compose.ui.graphics.Color(0xFF10B981)
    val tertiaryLight = androidx.compose.ui.graphics.Color(0xFFEC4899)
    val backgroundLight = androidx.compose.ui.graphics.Color(0xFFF6F8FF)
    val surfaceLight = androidx.compose.ui.graphics.Color(0xFFFFFFFF)
    val onPrimaryLight = androidx.compose.ui.graphics.Color(0xFFFFFFFF)
    val onSurfaceLight = androidx.compose.ui.graphics.Color(0xFF111827)
    val onBackgroundLight = androidx.compose.ui.graphics.Color(0xFF111827)
    val outlineLight = androidx.compose.ui.graphics.Color(0xFFD1D5DB)
    val surfaceVariantLight = androidx.compose.ui.graphics.Color(0xFFE5E7EB)
}

private val Typography = androidx.compose.material3.Typography()
