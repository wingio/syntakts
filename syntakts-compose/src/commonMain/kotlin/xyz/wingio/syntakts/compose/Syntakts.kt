package xyz.wingio.syntakts.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.AnnotatedString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import xyz.wingio.syntakts.Syntakts
import xyz.wingio.syntakts.compose.clickable.ClickHandlerStore
import xyz.wingio.syntakts.compose.style.AnnotatedStyledTextBuilder
import xyz.wingio.syntakts.compose.style.ComposeFontResolver
import xyz.wingio.syntakts.compose.style.DefaultFontResolver
import xyz.wingio.syntakts.syntakts

/**
 * Remembered DSL for [Syntakts.Builder], the preferred way to create a [Syntakts] instance in compose
 *
 * Simple example:
 * ```
 * val syntakts = rememberSyntakts {
 *      rule("@([A-z0-9_]+)") { result, context ->
 *          append(result.value)
 *      }
 * }
 * ```
 *
 * @see [rememberRendered]
 * @see [render]
 *
 * @param builder A lambda given an instance of [Syntakts.Builder], this is where you declare rules
 * @return [Syntakts]
 */
@Composable
public fun <C> rememberSyntakts(builder: Syntakts.Builder<C>.() -> Unit): Syntakts<C> = remember { syntakts(builder) }

/**
 * Parse and render the given [text] using the defined rules into an [AnnotatedString]
 * ---
 * Use [rememberRendered] when in a composable scope
 *
 * @see [rememberRendered]
 *
 * @param text What to parse and render
 * @param context Additional information that nodes may need to render
 * @param fontResolver Used to retrieve fonts from specified font names
 * @return [AnnotatedString] - The final rendered text to be used on a Text component
 */
public fun <C> Syntakts<C>.render(
    text: String,
    context: C,
    fontResolver: ComposeFontResolver = DefaultFontResolver,
    builder: AnnotatedStyledTextBuilder = AnnotatedStyledTextBuilder(fontResolver = fontResolver)
): AnnotatedString {
    val nodes = parse(text)
    for (node in nodes) {
        node.render(builder, context)
    }
    return builder.build()
}

/**
 * Remember the rendered text, only updates when either [text] or [context] updates
 *
 * @see [rememberAsyncRendered]
 *
 * @param text What to parse and render
 * @param context Additional information that nodes may need to render
 * @param fontResolver Used to retrieve fonts from specified font names
 * @return [AnnotatedString] - The final rendered text to be used on a Text component
 */
@Composable
public fun <C> Syntakts<C>.rememberRendered(
    text: String,
    context: C,
    fontResolver: ComposeFontResolver = DefaultFontResolver
): AnnotatedString {
    val builder = remember { AnnotatedStyledTextBuilder(fontResolver = fontResolver) }

    LaunchedEffect(text, context) {
        builder.clear()
        ClickHandlerStore.clearForBuilder(builder.id)
    }

    DisposableEffect(Unit) {
        onDispose {
            ClickHandlerStore.clearForBuilder(builder.id)
        }
    }

    return remember(text, context, fontResolver) {
        render(text, context, fontResolver, builder)
    }
}

/**
 * Remember the rendered text, only updates when either [text] or [context] updates
 * ---
 * This will do the text parsing in a separate thread and update the text when the rendering finishes
 *
 * @param text What to parse and render
 * @param context Additional information that nodes may need to render
 * @param fontResolver Used to retrieve fonts from specified font names
 * @return [AnnotatedString] - The final rendered text to be used on a Text component
 */
@Composable
public fun <C> Syntakts<C>.rememberAsyncRendered(
    text: String,
    context: C,
    fontResolver: ComposeFontResolver = DefaultFontResolver
): AnnotatedString {
    var parsedText by remember { mutableStateOf(AnnotatedString(text)) }
    val builder = remember { AnnotatedStyledTextBuilder(fontResolver = fontResolver) }

    LaunchedEffect(text, context) {
        builder.clear()
        ClickHandlerStore.clearForBuilder(builder.id)
        launch(Dispatchers.IO) {
            parsedText = render(text, context, fontResolver, builder)
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            ClickHandlerStore.clearForBuilder(builder.id)
        }
    }

    return parsedText
}

/**
 * Remember the rendered text, only updates when [text] updates
 *
 * @param text What to parse and render
 * @param fontResolver Used to retrieve fonts from specified font names
 * @return [AnnotatedString] - The final rendered text to be used on a Text or [ClickableText][xyz.wingio.syntakts.compose.clickable.ClickableText] component
 */
@Composable
public fun Syntakts<Unit>.rememberRendered(
    text: String,
    fontResolver: ComposeFontResolver = DefaultFontResolver
): AnnotatedString = rememberRendered(text, Unit, fontResolver)