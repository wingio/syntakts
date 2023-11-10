package xyz.wingio.syntakts.style

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

/**
 * Multiplatform representation of font weight
 * 
 * @param weight Thickness of the glyphs, between 1 and 1000
 */
@Stable
@Immutable
public data class FontWeight(
    public val weight: Int
) {
    
    public companion object {
        /**
         * [Thin]
         */
        @Stable
        public val W100: FontWeight = FontWeight(100)

        /**
         * [ExtraLight]
         */
        @Stable
        public val W200: FontWeight = FontWeight(200)

        /**
         * [Light]
         */
        @Stable
        public val W300: FontWeight = FontWeight(300)

        /**
         * [Normal], regular, default
         */
        @Stable
        public val W400: FontWeight = FontWeight(400)

        /**
         * [Medium]
         */
        @Stable
        public val W500: FontWeight = FontWeight(500)

        /**
         * [SemiBold]
         */
        @Stable
        public val W600: FontWeight = FontWeight(600)

        /**
         * [Bold]
         */
        @Stable
        public val W700: FontWeight = FontWeight(700)

        /**
         * [ExtraBold]
         */
        @Stable
        public val W800: FontWeight = FontWeight(800)

        /**
         * [Black]
         */
        @Stable
        public val W900: FontWeight = FontWeight(900)

        /**
         * Alias for [W100]
         */
        @Stable
        public val Thin: FontWeight = W100

        /**
         * Alias for [W200]
         */
        @Stable
        public val ExtraLight: FontWeight = W200

        /**
         * Alias for [W300]
         */
        @Stable
        public val Light: FontWeight = W300

        /**
         * Alias for [W400]
         */
        @Stable
        public val Normal: FontWeight = W400

        /**
         * Alias for [W500]
         */
        @Stable
        public val Medium: FontWeight = W500

        /**
         * Alias for [W600]
         */
        @Stable
        public val SemiBold: FontWeight = W600

        /**
         * Alias for [W700]
         */
        @Stable
        public val Bold: FontWeight = W700

        /**
         * Alias for [W800]
         */
        @Stable
        public val ExtraBold: FontWeight = W800

        /**
         * Alias for [W900]
         */
        @Stable
        public val Black: FontWeight = W900

        @Stable
        public val values: List<FontWeight> = listOf(W100, W200, W300, W400, W500, W600, W700, W800, W900)
    }
    
    init {
        require(weight in 1..1000) {
            "Font weight must be between 1 and 1000, currently: $weight"
        }
    }
    
}