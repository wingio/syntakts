package xyz.wingio.syntakts.android

import android.content.Context
import android.widget.TextView
import xyz.wingio.syntakts.Syntakts
import xyz.wingio.syntakts.android.style.SpannableStyledTextBuilder

public fun <C> Syntakts<C>.render(text: String, context: C, androidContext: Context): CharSequence {
    val builder = SpannableStyledTextBuilder(androidContext)
    val nodes = parse(text)
    for (node in nodes) {
        node.render(builder, context)
    }
    return builder.build()
}

public fun Syntakts<Unit>.render(text: String, androidContext: Context): CharSequence =
    render(text, Unit, androidContext)

public fun <C> TextView.render(
    text: String,
    syntakts: Syntakts<C>,
    context: C,
    enableClickable: Boolean = false
) {
    setText(syntakts.render(text, context, getContext()))
    if (enableClickable) {
        movementMethod = ClickableMovementMethod()
    }
}