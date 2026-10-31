package com.example.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = NeonCyan,
    onPrimary = CyberBlack,
    primaryContainer = Color.Transparent,
    onPrimaryContainer = NeonCyan,
    secondary = AcidGreen,
    onSecondary = CyberBlack,
    tertiary = WarningOrange,
    onTertiary = CyberBlack,
    background = CyberBlack,
    onBackground = TerminalText,
    surface = CyberDarkGray,
    onSurface = TerminalText,
    surfaceVariant = CyberGray,
    onSurfaceVariant = TerminalDim,
    outline = NeonCyan.copy(alpha = 0.5f),
    error = WarningOrange,
    onError = CyberBlack
)

// We focus on dark theme as requested, but provide a basic light fallback
private val LightColorScheme = lightColorScheme(
    primary = NeonCyan,
    secondary = AcidGreen,
    tertiary = WarningOrange,
    background = Color(0xFFF0F0F0),
    surface = Color.White,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onTertiary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black
)

@Composable
fun RootMateTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // RootMate is Cyberpunk Utilitarian, we strongly prefer dark mode
    val colorScheme = if (darkTheme) DarkColorScheme else DarkColorScheme // Force dark for cyberpunk feel if user didn't specify, or just DarkColorScheme for now

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
