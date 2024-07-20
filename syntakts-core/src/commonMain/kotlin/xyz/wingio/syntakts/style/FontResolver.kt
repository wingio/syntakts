package xyz.wingio.syntakts.style

/**
 * Used to retrieve a platform or framework specific font representation from a font name
 */
public abstract class FontResolver<T> {

    protected abstract val fontMap: MutableMap<String, T>

    public abstract fun resolveFont(fontName: String): T?

    public abstract fun registerFont(fontName: String, platformFont: T)

    /**
     * Bulk register fonts
     */
    public fun register(vararg fonts: Pair<String, T>) {
        fontMap.putAll(fonts)
    }

}

/**
 * Default fonts included on most platforms
 */
public object Fonts {

    /**
     * The default font for the system
     */
    public const val DEFAULT: String = "default"

    /**
     * Default monospaced font for the system
     */
    public const val MONOSPACE: String = "monospace"

    /**
     * Default serif font for the system
     */
    public const val SERIF: String = "serif"

    /**
     * Default sans-serif font for the system
     */
    public const val SANS_SERIF: String = "sans-serif"

}