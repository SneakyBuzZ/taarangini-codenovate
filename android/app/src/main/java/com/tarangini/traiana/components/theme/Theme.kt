package com.tarangini.traiana.components.theme

import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
  primary = Colors.CoralBlue100,
  secondary = Colors.CoralGreen100,
  tertiary = Colors.Neutral100,
  background = Colors.Neutral900,
  surface = Colors.Neutral800,
  onPrimary = Color.White,
  onSecondary = Color.White,
  onTertiary = Color.Black,
  onBackground = Colors.Neutral300,
  onSurface = Colors.Neutral200
)

private val LightColorScheme = lightColorScheme(
  primary = Colors.CoralBlue100,
  secondary = Colors.CoralGreen100,
  tertiary = Colors.Neutral900,
  background = Colors.Neutral100,
  surface = Colors.Neutral200,
  onPrimary = Color.White,
  onSecondary = Color.White,
  onTertiary = Color.White,
  onBackground = Colors.Neutral800,
  onSurface = Colors.Neutral900
)

@Composable
fun AppTheme(
  darkTheme: Boolean = true,
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
    typography = Typography,
    content = content
  )
}
