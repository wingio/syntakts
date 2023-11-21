package xyz.wingio.syntakts.android

import android.content.Context
import android.widget.TextView
import xyz.wingio.syntakts.Syntakts
import xyz.wingio.syntakts.android.style.SpannableStyledTextBuilder

/**
 * Parse and render the given [text] using the defined rules into a SpannableString
 *
 * @param text What to parse and render
 * @param context Additional information that nodes may need to render
 * @param androidContext Necessary for certain text measurements
 * @return SpannableString as a [CharSequence]
 */
public fun <C> Syntakts<C>.render(text: CharSequence, context: C, androidContext: Context): CharSequence {
    val builder = SpannableStyledTextBuilder(androidContext)
    val nodes = parse(text)
    for (node in nodes) {
        node.render(builder, context)
    }
    return builder.build()
}

/**
 * Parse and render the given [text] using the defined rules into a SpannableString
 *
 * @param text What to parse and render
 * @param androidContext Necessary for certain text measurements
 * @return SpannableString as a [CharSequence]
 */
public fun Syntakts<Unit>.render(text: CharSequence, androidContext: Context): CharSequence =
    render(text, Unit, androidContext)

/**
 * Parse and render the given [text] using the [syntakts] onto this [TextView]
 *
 * @param text What to parse and render
 * @param syntakts An instance of [Syntakts] with the desired rules
 * @param context Additional information that nodes may need to render
 * @param enableClickable (optional) Whether or not to process click and long click events
 */
public fun <C> TextView.render(
    text: CharSequence,
    syntakts: Syntakts<C>,
    context: C,
    enableClickable: Boolean = false
) {
    setText(syntakts.render(text, context, getContext()))
    if (enableClickable) {
        movementMethod = ClickableMovementMethod()
    }
}

/**
 * Parse and render the given [text] using the [syntakts] onto this [TextView]
 *
 * @param text What to parse and render
 * @param syntakts An instance of [Syntakts] with the desired rules
 * @param enableClickable (optional) Whether or not to process click and long click events
 */
public fun TextView.render(
    text: CharSequence,
    syntakts: Syntakts<Unit>,
    enableClickable: Boolean = false
) {
    setText(syntakts.render(text, context))
    if (enableClickable) {
        movementMethod = ClickableMovementMethod()
    }
}