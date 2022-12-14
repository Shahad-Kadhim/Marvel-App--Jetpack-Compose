package com.shahad.app.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import okhttp3.Cookie

private val DarkColorPalette = darkColors(
    primary = brandColor,
    primaryVariant = primaryShadeNight,
    secondary =  backgroundCardNight,
    background = backgroundColorNight,
    onSecondary = textColorNight,
    onBackground = textColorNight,
    onPrimary = Color.White,
    surface = Color.DarkGray
)
@SuppressLint("ConflictingOnColor")
private val LightColorPalette = lightColors(
    primary = brandColor,
    primaryVariant = primaryShadeLight,
    secondary = backgroundCardLight,
    background = backgroundColorLight,
    onSecondary = textColorLight,
    onBackground = textColorLight,
    onPrimary = Color.White ,
    surface = Color.LightGray
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