package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = GoldPrimary,
    onPrimary = Color(0xFF1E1400),
    primaryContainer = AmberAccent,
    onPrimaryContainer = Color.White,
    secondary = ElectricPurple,
    onSecondary = Color.White,
    secondaryContainer = DeepPurpleDark,
    onSecondaryContainer = Color(0xFFE8DDFF),
    tertiary = NeonOrange,
    onTertiary = Color.White,
    background = RoyalNightBg,
    onBackground = TextPrimaryDark,
    surface = SurfaceDark,
    onSurface = TextPrimaryDark,
    surfaceVariant = SurfaceCardDark,
    onSurfaceVariant = TextSecondaryDark,
    outline = SurfaceBorderDark,
    error = ErrorRed,
    onError = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = GoldPrimaryLight,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFFE082),
    onPrimaryContainer = Color(0xFF3E2723),
    secondary = RoyalPurpleLight,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFEDE7F6),
    onSecondaryContainer = Color(0xFF1A0A40),
    tertiary = SaffronFestive,
    onTertiary = Color.White,
    background = BackgroundLight,
    onBackground = Color(0xFF1C1B1F),
    surface = SurfaceLight,
    onSurface = Color(0xFF1C1B1F),
    surfaceVariant = SurfaceCardLight,
    onSurfaceVariant = Color(0xFF49454F),
    outline = Color(0xFF79747E),
    error = ErrorRed,
    onError = Color.White
)

@Composable
fun BalaJiDjTheme(
    darkTheme: Boolean = true, // Default to rich dark concert & DJ celebration theme
    dynamicColor: Boolean = false, // Keep consistent branded DJ palette
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

// Alias for template compatibility
@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    BalaJiDjTheme(darkTheme = darkTheme, dynamicColor = dynamicColor, content = content)
}
