package xyz.wingio.syntakts.android.style

import androidx.annotation.ColorInt
import androidx.annotation.ColorLong
import xyz.wingio.syntakts.style.Color

/**
 * Convert a [Color] to an Android color int (0xAARRGGBB)
 */
@ColorInt
public fun Color.toAndroidColorInt(): Int {
    return (
        (alpha shl 24) or
        (red shl 16) or
        (green shl 8) or
        blue
    )
}

/**
 * Converts a color formatted [Long] to a [Color]
 */
public fun @receiver:ColorLong Long.toSyntaktsColor(): Color {
    val alpha = shr(24) and 0xFF
    val red = shr(16) and 0xFF
    val green = shr(8) and 0xFF
    val blue = and(0xFF)

    return Color(
        red = red.toInt(),
        green = green.toInt(),
        blue = blue.toInt(),
        alpha = alpha.toInt()
    )
}

/**
 * Creates a [Color] from a color long
 */
public fun Color.Companion.fromAndroidColorLong(@ColorLong colorLong: Long): Color =
    colorLong.toSyntaktsColor()