package xyz.wingio.syntakts.style

import androidx.compose.runtime.Immutable

/**
 * Defines a line to be drawn through the text
 */
public enum class TextDecoration {
    None,

    /**
     * Draws a line below the text
     */
    Underline,

    /**
     * Draws a line over the text
     */
    LineThrough;

    public companion object {

        /**
         * Draws a line over the text, alias of [LineThrough]
         */
        public val StrikeThrough: TextDecoration = LineThrough

    }
}