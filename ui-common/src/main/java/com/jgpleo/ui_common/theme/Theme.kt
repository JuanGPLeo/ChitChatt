package com.jgpleo.ui_common.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColors(
    primary = PrimaryColor,
    primaryVariant = PrimaryColorVariant,
    secondary = ElementsBackgroundDark,

    background = ScreenBackgroundDark,
    onPrimary = Color.White,
    onBackground = Gray200
)

private val LightColorPalette = lightColors(
    primary = PrimaryColor,
    primaryVariant = PrimaryColorVariant,
    secondary = ElementsBackgroundLight,

    background = ScreenBackgroundLight,
    onPrimary = Color.White,
    onBackground = Gray800
)

@Composable
fun ChitChattTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    val typography = if (darkTheme) {
        TypographyDark
    } else {
        TypographyLight
    }

    val systemUiController = rememberSystemUiController()
    if (darkTheme) {
        systemUiController.setSystemBarsColor(color = ScreenBackgroundDark)
        systemUiController.setNavigationBarColor(color = ScreenBackgroundDark)
    } else {
        systemUiController.setSystemBarsColor(color = Color.White)
        systemUiController.setNavigationBarColor(color = Color.White)
    }

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = Shapes,
        content = content
    )
}