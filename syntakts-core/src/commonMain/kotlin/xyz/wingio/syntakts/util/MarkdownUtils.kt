package xyz.wingio.syntakts.util

import xyz.wingio.syntakts.style.FontWeight
import xyz.wingio.syntakts.style.Style
import xyz.wingio.syntakts.style.em
import xyz.wingio.syntakts.style.sp

internal fun hashCountToStyle(hashCount: Int): Style {
    return when(hashCount) {
        1 -> Style(
            fontWeight = FontWeight.SemiBold,
            fontSize = 2.2.em
        )
        2 -> Style(
            fontWeight = FontWeight.SemiBold,
            fontSize = 1.7.em
        )
        3 -> Style(
            fontWeight = FontWeight.SemiBold,
            fontSize = 1.5.em
        )
        4 -> Style(
            fontWeight = FontWeight.SemiBold,
            fontSize = 1.4.em
        )
        5 -> Style(
            fontWeight = FontWeight.SemiBold,
            fontSize = 1.2.em
        )
        else -> Style(
            fontWeight = FontWeight.SemiBold,
            fontSize = 1.1.em
        )
    }.paragraph { lineHeight = 1.1f.em }
}