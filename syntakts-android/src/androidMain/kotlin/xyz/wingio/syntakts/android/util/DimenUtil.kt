package xyz.wingio.syntakts.android.util

import android.content.Context
import android.util.TypedValue

internal fun spToEm(
    sp: Float,
    textSize: Float
) = sp / textSize

internal fun emToPx(em: Float, textSize: Float): Float {
    return em * textSize
}

internal fun Context.spToPx(sp: Float): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        sp,
        resources.displayMetrics
    )
}