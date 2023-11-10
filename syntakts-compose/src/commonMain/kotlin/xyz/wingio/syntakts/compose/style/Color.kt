package xyz.wingio.syntakts.compose.style

import androidx.compose.ui.graphics.Color as ComposeColor
import xyz.wingio.syntakts.style.Color

/**
 * Converts a [Color] to it's compose representation
 */
public fun Color?.toComposeColor(): ComposeColor {
    if(this == null || ignore) return ComposeColor.Unspecified
    return ComposeColor(red, green, blue, alpha)
}

/**
 * Converts a [Color][ComposeColor] to it's Syntakts representation
 */
public fun ComposeColor.toSyntaktsColor(): Color = Color(
    red = (red * 255).toInt(),
    green = (green * 255).toInt(),
    blue = (blue * 255).toInt(),
    alpha = (alpha * 255).toInt()
)