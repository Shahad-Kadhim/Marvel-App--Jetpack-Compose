package com.shahad.app.marvelapp.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)


data class Colors(
    val brandColor: Color = Color(0xFFF60404),
    val primaryShadeLight: Color = Color(0xDEC73232),
    val secondaryColorLight: Color = Color(0x99C73232),
    val thirdColorLight: Color = Color(0x61B52D2D),
    val primaryShadeNight: Color = Color(0xDEF0F0DF),
    val secondaryColorNight: Color = Color(0x99FBEFEF),
    val thirdColorNight: Color = Color(0x61FBEFEF),
    val backgroundColorLight: Color = Color(0xFFF4F2F3),
    val backgroundColorNight: Color = Color(0xFF070607),
    val backgroundCardLight: Color = Color.White,
    val backgroundCardNight: Color = Color.Black,
)

val LocalColors = compositionLocalOf { Colors()}

val MaterialTheme.Colors: Colors
    @Composable
    @ReadOnlyComposable
    get() = LocalColors.current
