package xyz.wingio.syntakts.compose.style

import androidx.compose.ui.text.font.FontFamily
import xyz.wingio.syntakts.style.FontResolver
import xyz.wingio.syntakts.style.Fonts

/**
 * Resolves fonts into a [FontFamily] from a font name
 *
 * For most cases it is reccommended to use [DefaultFontResolver] to register your fonts
 */
public class ComposeFontResolver: FontResolver<FontFamily>() {

    override val fontMap: MutableMap<String, FontFamily> = mutableMapOf(
        Fonts.DEFAULT to FontFamily.Default,
        Fonts.MONOSPACE to FontFamily.Monospace,
        "monospaced" to FontFamily.Monospace,
        Fonts.SERIF to FontFamily.Serif,
        Fonts.SANS_SERIF to FontFamily.SansSerif
    )

    /**
     * Gets a [FontFamily] from the given [fontName]
     *
     * @param fontName Case-insensitive name for the desired font
     */
    override fun resolveFont(fontName: String): FontFamily? {
        return fontMap[fontName.lowercase()]
    }

    /**
     * Registers a [FontFamily] with a specific [fontName]
     *
     * @param fontName Case-insensitive name for the [font][platformFont]
     * @param platformFont The [FontFamily] to assign to the provided [fontName]
     */
    override fun registerFont(fontName: String, platformFont: FontFamily) {
        fontMap[fontName.lowercase()] = platformFont
    }

}

/**
 * Default [FontResolver] used when rendering
 */
public val DefaultFontResolver: ComposeFontResolver = ComposeFontResolver()