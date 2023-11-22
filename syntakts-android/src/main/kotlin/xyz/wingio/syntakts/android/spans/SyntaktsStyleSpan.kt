package xyz.wingio.syntakts.android.spans

import android.content.Context
import android.graphics.Typeface
import android.text.TextPaint
import android.text.style.MetricAffectingSpan
import androidx.core.graphics.TypefaceCompat
import xyz.wingio.syntakts.android.style.toAndroidColorInt
import xyz.wingio.syntakts.android.util.emToPx
import xyz.wingio.syntakts.android.util.spToEm
import xyz.wingio.syntakts.android.util.spToPx
import xyz.wingio.syntakts.style.FontStyle
import xyz.wingio.syntakts.style.Style
import xyz.wingio.syntakts.style.TextDecoration
import xyz.wingio.syntakts.style.TextUnit
import kotlin.math.roundToInt

/**
 * Span that applies a [Style]
 *
 * @param style The [Style] to apply
 * @param context Necessary for certain measurements
 */
public open class SyntaktsStyleSpan(
    public val style: Style,
    public val context: Context
) : MetricAffectingSpan() {

    override fun updateDrawState(tp: TextPaint?) {
        apply(tp, style, context)
    }

    override fun updateMeasureState(textPaint: TextPaint) {
        apply(textPaint, style, context)
    }

    public companion object {

        /**
         * Applies a given [style] to a [paint]
         *
         * @param paint Information for how text can be displayed
         * @param style The [Style] to apply
         * @param context Necessary for certain measurements
         */
        public fun apply(paint: TextPaint?, style: Style, context: Context) {
            if (paint == null) return

            with(style) {
                color?.let { color ->
                    paint.setColor(color.toAndroidColorInt())
                }

                background?.let { background ->
                    paint.bgColor = background.toAndroidColorInt()
                }

                if (fontSize !is TextUnit.Unspecified) {
                    paint.textSize = when (fontSize.unit) {
                        "sp" -> context.spToPx(fontSize.value)
                        "em" -> emToPx(fontSize.value, paint.textSize)
                        else -> paint.letterSpacing
                    }
                }

                fontWeight?.let { fontWeight ->
                    paint.typeface =
                        TypefaceCompat.create(context, paint.typeface, fontWeight.weight, false)
                }

                paint.typeface = when {
                    fontWeight != null && fontStyle != null -> TypefaceCompat.create(
                        context,
                        paint.typeface,
                        fontWeight!!.weight,
                        fontStyle!! == FontStyle.Italic
                    )

                    fontWeight != null -> TypefaceCompat.create(
                        context,
                        paint.typeface,
                        fontWeight!!.weight,
                        false
                    )

                    fontStyle != null -> TypefaceCompat.create(
                        context,
                        paint.typeface,
                        Typeface.ITALIC
                    )

                    else -> paint.typeface
                }

                if (letterSpacing !is TextUnit.Unspecified) {
                    paint.letterSpacing = when (letterSpacing.unit) {
                        "sp" -> spToEm(letterSpacing.value, paint.textSize)
                        "em" -> letterSpacing.value
                        else -> paint.letterSpacing
                    }
                }

                when (textDecoration) {
                    TextDecoration.Underline -> paint.isUnderlineText = true
                    TextDecoration.LineThrough -> paint.isStrikeThruText = true
                    else -> {}
                }

                paragraphStyle?.let { paragraphStyle ->
                    when (paragraphStyle.lineHeight.unit) {
                        "sp" -> paint.applyLineHeight(context.spToPx(paragraphStyle.lineHeight.value))
                        "em" -> paint.applyLineHeight(emToPx(paragraphStyle.lineHeight.value, paint.textSize))
                    }
                }
            }
        }

    }

}

/**
 * Applies the given [lineHeight] to this paint
 *
 * @param lineHeight The line height for this text (in pixels)
 */
internal fun TextPaint.applyLineHeight(lineHeight: Float) {
    val originHeight = fontMetricsInt.descent - fontMetricsInt.ascent

    if (originHeight <= 0) {
        return
    }

    val ratio: Float = lineHeight * 1.0f / originHeight
    fontMetricsInt.descent = (ratio * fontMetricsInt.descent).roundToInt()
    fontMetricsInt.ascent = (fontMetricsInt.descent - lineHeight).roundToInt()
}