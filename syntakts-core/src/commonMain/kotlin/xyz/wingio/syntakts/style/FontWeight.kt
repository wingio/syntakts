package xyz.wingio.syntakts.style

/**
 * Multiplatform representation of font weight
 * 
 * @param weight Thickness of the glyphs, between 1 and 1000
 */
public data class FontWeight(
    public val weight: Int
) {
    
    public companion object {
        /**
         * [Thin]
         */
        public val W100: FontWeight = FontWeight(100)

        /**
         * [ExtraLight]
         */
        public val W200: FontWeight = FontWeight(200)

        /**
         * [Light]
         */
        public val W300: FontWeight = FontWeight(300)

        /**
         * [Normal], regular, default
         */
        public val W400: FontWeight = FontWeight(400)

        /**
         * [Medium]
         */
        public val W500: FontWeight = FontWeight(500)

        /**
         * [SemiBold]
         */
        public val W600: FontWeight = FontWeight(600)

        /**
         * [Bold]
         */
        public val W700: FontWeight = FontWeight(700)

        /**
         * [ExtraBold]
         */
        public val W800: FontWeight = FontWeight(800)

        /**
         * [Black]
         */
        public val W900: FontWeight = FontWeight(900)

        /**
         * Alias for [W100]
         */
        public val Thin: FontWeight = W100

        /**
         * Alias for [W200]
         */
        public val ExtraLight: FontWeight = W200

        /**
         * Alias for [W300]
         */
        public val Light: FontWeight = W300

        /**
         * Alias for [W400]
         */
        public val Normal: FontWeight = W400

        /**
         * Alias for [W500]
         */
        public val Medium: FontWeight = W500

        /**
         * Alias for [W600]
         */
        public val SemiBold: FontWeight = W600

        /**
         * Alias for [W700]
         */
        public val Bold: FontWeight = W700

        /**
         * Alias for [W800]
         */
        public val ExtraBold: FontWeight = W800

        /**
         * Alias for [W900]
         */
        public val Black: FontWeight = W900

        
        public val values: List<FontWeight> = listOf(W100, W200, W300, W400, W500, W600, W700, W800, W900)
    }
    
    init {
        require(weight in 1..1000) {
            "Font weight must be between 1 and 1000, currently: $weight"
        }
    }
    
}