package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme =
  darkColorScheme(
    primary = PurplePrimaryDark,
    onPrimary = PurpleOnPrimaryDark,
    primaryContainer = PurpleContainerDark,
    onPrimaryContainer = PurpleOnContainerDark,
    background = PurpleBackgroundDark,
    surface = PurpleSurfaceDark,
    onSurface = PurpleOnSurfaceDark,
    onSurfaceVariant = PurpleOnSurfaceVariantDark,
    outline = PurpleOutlineDark,
  )

private val LightColorScheme =
  lightColorScheme(
    primary = PurplePrimaryLight,
    onPrimary = PurpleOnPrimaryLight,
    primaryContainer = PurpleContainerLight,
    onPrimaryContainer = PurpleOnContainerLight,
    background = PurpleBackgroundLight,
    surface = PurpleSurfaceLight,
    onSurface = PurpleOnSurfaceLight,
    onSurfaceVariant = PurpleOnSurfaceVariantLight,
    outline = PurpleOutlineLight,
  )

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  // Dynamic color can be disabled to preserve the curated design theme
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  val colorScheme =
    when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }

      darkTheme -> DarkColorScheme
      else -> LightColorScheme
    }

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}
