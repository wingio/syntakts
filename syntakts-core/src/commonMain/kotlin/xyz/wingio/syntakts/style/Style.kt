package xyz.wingio.syntakts.style

import androidx.compose.runtime.Stable

/**
 * Collection of properties that can be used to style text
 *
 * @param color Color to use for the text itself
 * @param background Color to use for the background of the text
 * @param fontSize The size of the text
 * @param fontWeight Thickness of the glyphs (Ex [bold][FontWeight.Bold])
 * @param fontStyle Variant of the font (Ex [italic][FontStyle.Italic])
 * @param letterSpacing Spacing between each character
 * @param paragraphStyle Set of styles for blocks of text, applying could separate the text as if a line feed was added
 * @param font The font or font family for the text, see [Fonts] for default values
 */
@Stable
public data class Style(
    var color: Color? = null,
    var background: Color? = null,
    var fontSize: TextUnit = TextUnit.Unspecified,
    var fontWeight: FontWeight? = null,
    var fontStyle: FontStyle? = null,
    var letterSpacing: TextUnit = TextUnit.Unspecified,
    var textDecoration: TextDecoration? = null,
    var paragraphStyle: ParagraphStyle? = null,
    var font: String? = null
) {

    /**
     * Apply paragraph styles
     *
     * @param styleBlock lambda that lets you easily set paragraph styles
     */
    public fun paragraph(styleBlock: ParagraphStyle.() -> Unit): Style {
        paragraphStyle = paragraphStyle?.apply(styleBlock) ?: ParagraphStyle().apply(styleBlock)
        return this
    }

}

/**
 * Set of styles for blocks of text, applying could separate the text as if a line feed was added
 *
 * @param lineHeight Line height for a paragraph in either [sp][Sp] or [em][Em]
 */
@Stable
public data class ParagraphStyle(
    var lineHeight: TextUnit = TextUnit.Unspecified
)