package xyz.wingio.syntakts.android.util

import android.content.Context
import android.util.TypedValue

/**
 * Converts sp units to em
 *
 * @param textSize Size of the text, in pixels
 */
internal fun spToEm(
    sp: Float,
    textSize: Float
) = sp / textSize

/**
 * Converts em units to px
 *
 * @param textSize Size of the text, in pixels
 * @return Em size in pixels
 */
internal fun emToPx(em: Float, textSize: Float): Float {
    return em * textSize
}

/**
 * Converts sp units to px
 *
 * @return sp size in pixels
 */
internal fun Context.spToPx(sp: Float): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        sp,
        resources.displayMetrics
    )
}