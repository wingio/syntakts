package xyz.wingio.syntakts.style

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import kotlin.math.roundToInt

/**
 * Multiplatform representation of a color
 *
 * @param red Number representing how much red this color has (0-255)
 * @param green Number representing how much red this color has (0-255)
 * @param blue Number representing how much red this color has (0-255)
 * @param alpha Number representing how much opacity this color has (0-255)
 * @param ignore Whether or not to ignore this color
 */
@Stable
@Immutable
public data class Color(
    /* @IntRange(0, 255) */
    val red: Int,
    /* @IntRange(0, 255) */
    val green: Int,
    /* @IntRange(0, 255) */
    val blue: Int,
    /* @IntRange(0, 255) */
    val alpha: Int = 255,
    val ignore: Boolean = false
) {

    public companion object {

        public val BLACK: Color = Color(0, 0, 0)
        public val DARK_GRAY: Color = Color(68, 68, 68)
        public val GRAY: Color = Color(136, 136, 136)
        public val LIGHT_GRAY: Color = Color(204, 204, 204)
        public val WHITE: Color = Color(255, 255, 255)

        public val RED: Color = Color(255, 0, 0)
        public val GREEN: Color = Color(0, 255, 0)
        public val BLUE: Color = Color(0, 0, 255)
        public val YELLOW: Color = Color(255, 255, 0)
        public val CYAN: Color = Color(0, 255, 255)
        public val MAGENTA: Color = Color(255, 0, 255)

        public val TRANSPARENT: Color = Color(0, 0, 0, 0)

        /**
         * Refers to a nonexistent color
         */
        public val UNSPECIFIED: Color = Color(0, 0, 0, 0, ignore = true)

    }

    override fun toString(): String {
        val sb = StringBuilder("Color(\n")
        sb.append("\tred = $red,\n")
        sb.append("\tgreen = $green,\n")
        sb.append("\tblue = $blue,\n")
        sb.append("\talpha = $alpha,\n")
        sb.append("\thexCode = \"$hexCode\"\n")
        sb.append(")")
        return sb.toString()
    }

    /**
     * Hex color code representation (#RRGGBBAA)
     */
    val hexCode: String = StringBuilder("#").apply {
        append("%x".format(red).padStart(2, '0'))
        append("%x".format(green).padStart(2, '0'))
        append("%x".format(blue).padStart(2, '0'))
        append("%x".format(alpha).padStart(2, '0'))
    }.toString()

    /**
     * Apply a level of opacity to this [Color]
     */
    public infix fun alpha(alpha: Float): Color = withOpacity(alpha)

    /**
     * Apply a level of [opacity] to this [Color]
     */
    public infix fun withOpacity(opacity: Float): Color  {
        assert(opacity in 0f..1f) { "Opacity must be a value between 0 and 1" }
        return copy(alpha = (255 * opacity).roundToInt())
    }

}