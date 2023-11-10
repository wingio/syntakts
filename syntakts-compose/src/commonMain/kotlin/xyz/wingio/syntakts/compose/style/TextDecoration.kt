package xyz.wingio.syntakts.compose.style

import androidx.compose.ui.text.style.TextDecoration as ComposeTextDecoration
import xyz.wingio.syntakts.style.TextDecoration

/**
 * Converts [TextDecoration] to its compose representation
 */
public fun TextDecoration.toComposeTextDecoration(): ComposeTextDecoration = when(this) {
    TextDecoration.None -> ComposeTextDecoration.None
    TextDecoration.Underline -> ComposeTextDecoration.Underline
    TextDecoration.LineThrough -> ComposeTextDecoration.LineThrough
}

/**
 * Converts a [ComposeTextDecoration] to its Syntakts representation
 */
public fun ComposeTextDecoration.toSyntaktsTextDecoration(): TextDecoration = when(this) {
    ComposeTextDecoration.None -> TextDecoration.None
    ComposeTextDecoration.Underline -> TextDecoration.Underline
    ComposeTextDecoration.LineThrough -> TextDecoration.LineThrough
    else -> TextDecoration.None
}