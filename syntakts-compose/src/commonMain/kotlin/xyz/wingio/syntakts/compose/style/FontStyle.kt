package xyz.wingio.syntakts.compose.style

import androidx.compose.ui.text.font.FontStyle as ComposeFontStyle
import xyz.wingio.syntakts.style.FontStyle

/**
 * Converts a [FontStyle] to it's compose representation
 */
public fun FontStyle.toComposeFontStyle(): ComposeFontStyle = when(this) {
    FontStyle.Normal -> ComposeFontStyle.Normal
    FontStyle.Italic -> ComposeFontStyle.Italic
}

/**
 * Converts a [ComposeFontStyle] to it's Syntakts representation
 */
public fun ComposeFontStyle.toComposeFontStyle(): FontStyle = when(this) {
    ComposeFontStyle.Normal -> FontStyle.Normal
    ComposeFontStyle.Italic -> FontStyle.Italic
    else -> FontStyle.Normal
}