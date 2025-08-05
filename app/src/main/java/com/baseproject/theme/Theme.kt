package com.baseproject.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = primaryColor,
    onPrimary = surfaceColorDark,
    primaryContainer = secondaryColor,
    onPrimaryContainer = onSecondaryColor,
    secondary = secondaryColor,
    onSecondary = surfaceColorDark,
    secondaryContainer = secondaryColorLight,
    onSecondaryContainer = onSecondaryColor,
    surface = colorDark,
    surfaceContainerLow = surfaceColorDark,
    onTertiaryContainer = secondaryColorLight,
    background = colorDark,
    error = errorColor,
    onError = colorWhite,
    outline = greyDarker,
    outlineVariant = greyLight
)

private val LightColorScheme = lightColorScheme(
    primary = primaryColor,
    onPrimary = onPrimaryColor,
    primaryContainer = secondaryColor,
    onPrimaryContainer = onSecondaryColor,
    onSecondary = onSecondaryColor,
    secondary = secondaryColor,
    secondaryContainer = secondaryColorLight,
    onSecondaryContainer = onSecondaryColor,
    surface = colorWhite,
    surfaceContainerLow = primaryColorLighter,
    onTertiaryContainer = surfaceColorDark,
    background = colorWhite,
    error = errorColor,
    onError = colorWhite,
    outline = grey,
    outlineVariant = greyLight
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
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
        typography = typography,
        content = content
    )
}