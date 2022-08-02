package com.shahad.app.marvelapp.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

private val DarkColorPalette = darkColors(
    primary = brandColor,
    primaryVariant = primaryShadeNight,
    secondary =  backgroundCardNight,
    background = backgroundColorNight,
    onSecondary = primaryShadeNight,
    onBackground = primaryShadeNight,
)
@SuppressLint("ConflictingOnColor")
private val LightColorPalette = lightColors(
    primary = brandColor,
    primaryVariant = primaryShadeLight,
    secondary = backgroundCardLight,
    background = backgroundColorLight,
    onSecondary = primaryShadeLight,
    onBackground = primaryShadeLight,
//    onSurface = Colors().brandColor,
)

@Composable
fun MarvelAppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    CompositionLocalProvider(
        LocalSpacing provides  Spacing(),
    ){
        MaterialTheme(
            colors = colors,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}