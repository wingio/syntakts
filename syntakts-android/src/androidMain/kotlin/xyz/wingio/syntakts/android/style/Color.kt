package xyz.wingio.syntakts.android.style

import androidx.annotation.ColorInt
import xyz.wingio.syntakts.style.Color

public fun Color.toAndroidColorInt(): Int {
    return (
        (alpha shl 24) or
        (red shl 16) or
        (green shl 8) or
        blue
    )
}

public fun @receiver:ColorInt Long.toSyntaktsColor(): Color {
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

public fun Color.Companion.fromAndroidColorInt(@ColorInt colorInt: Long): Color =
    colorInt.toSyntaktsColor()