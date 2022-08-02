package com.shahad.app.marvelapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary =Colors().backgroundColorNight,
    primaryVariant = Colors().brandColor,
    secondary =  Color.DarkGray,
    background = Colors().backgroundColorNight,
)

private val LightColorPalette = lightColors(
    primary = Colors().backgroundColorLight,
    primaryVariant = Colors().brandColor,
    secondary = Color.White,
    background = Colors().backgroundColorLight,
//    surface = Color.White,
//    onPrimary = Color.White,
//    onSecondary = Color.Black,
//    onBackground = Color.White,
    onSurface = Colors().brandColor,
//    */
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
        LocalColors provides Colors()
    ){
        MaterialTheme(
            colors = colors,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}