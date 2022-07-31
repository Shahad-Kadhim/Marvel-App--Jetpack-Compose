package com.shahad.app.marvelapp.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color



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
