package xyz.wingio.syntakts.android.style

import android.graphics.Typeface
import xyz.wingio.syntakts.style.FontResolver
import xyz.wingio.syntakts.style.Fonts

/**
 * Keeps a record of [Typefaces][Typeface] with a plain font name
 *
 * For most cases it is reccommended to use [DefaultFontResolver] to register your fonts
 */
public class AndroidFontResolver: FontResolver<Typeface>() {

    override val fontMap: MutableMap<String, Typeface> = mutableMapOf(
        Fonts.DEFAULT to Typeface.DEFAULT,
        Fonts.MONOSPACE to Typeface.MONOSPACE,
        "monospaced" to Typeface.MONOSPACE,
        Fonts.SERIF to Typeface.SERIF,
        Fonts.SANS_SERIF to Typeface.SANS_SERIF
    )

    /**
     * Gets a font in the [Typeface] format from a given name
     *
     * @param fontName Case-insensitive font name to look up
     */
    public override fun resolveFont(fontName: String): Typeface? {
        return fontMap[fontName.lowercase()]
    }

    /**
     * Registers a [Typeface] with the specified [name][fontName]
     *
     * @param fontName Case-insensitive name to use for the [font][platformFont]
     * @param platformFont The font to register with the [fontName]
     */
    override fun registerFont(fontName: String, platformFont: Typeface) {
        fontMap[fontName.lowercase()] = platformFont
    }


}

/**
 * Default [FontResolver] used when rendering
 */
public val DefaultFontResolver: AndroidFontResolver = AndroidFontResolver()