package xyz.wingio.syntakts.util

import xyz.wingio.syntakts.style.FontWeight
import xyz.wingio.syntakts.style.Style
import xyz.wingio.syntakts.style.em
import xyz.wingio.syntakts.style.sp

internal fun hashCountToStyle(hashCount: Int): Style {
    return when(hashCount) {
        1 -> Style(
            fontWeight = FontWeight.SemiBold,
            fontSize = 36.sp
        )
        2 -> Style(
            fontWeight = FontWeight.SemiBold,
            fontSize = 32.sp
        )
        3 -> Style(
            fontWeight = FontWeight.SemiBold,
            fontSize = 25.sp
        )
        4 -> Style(
            fontWeight = FontWeight.SemiBold,
            fontSize = 21.sp
        )
        5 -> Style(
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp
        )
        else -> Style(
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp
        )
    }.paragraph { lineHeight = 2f.em }
}