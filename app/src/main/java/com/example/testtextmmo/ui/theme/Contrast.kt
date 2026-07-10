package com.example.testtextmmo.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance

fun contrastTextOn(background: Color): Color =
    if (background.luminance() > 0.45f) Color(0xFF12131F) else Color(0xFFF4F0FF)

fun contrastMutedOn(background: Color): Color =
    if (background.luminance() > 0.45f) Color(0xFF3D3A50) else Color(0xFFC8C2D8)

fun contrastOutlineOn(background: Color): Color =
    if (background.luminance() > 0.45f) Color(0x33000000) else Color(0x40FFFFFF)
