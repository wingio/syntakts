package xyz.wingio.syntakts.compose.clickable

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import xyz.wingio.syntakts.compose.style.CLICKABLE_ANNOTATION_TAG
import xyz.wingio.syntakts.compose.style.LONG_CLICKABLE_ANNOTATION_TAG

/**
 * Basic Text component that enabled support for Syntakts click handling
 * ---
 * **Does not support Material theming out of the box**
 *
 * @param text The rendered text
 * @param modifier Used to alter how the component looks or behaves
 * @param color Color of the text
 * @param fontSize Size of the text
 * @param fontWeight The thickness of the font glyphs
 * @param fontStyle Whether the font is italic or normal
 * @param fontFamily The specific fonts that should be used
 * @param letterSpacing Spacing between each character
 * @param textDecoration Used to draw a line over the text
 * @param textAlign How the text should be aligned
 * @param lineHeight Line height for the paragraph
 * @param style Styling configuration for the text
 * @param overflow How the text should handle visual overflow
 * @param softWrap Whether the text should break at soft line breaks
 * @param maxLines An optional maximum number of lines for the text to span, wrapping if necessary
 * @param inlineContent A map store composables that replaces certain ranges of the text
 */
@Composable
public fun ClickableText(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontWeight: FontWeight? = null,
    fontStyle: FontStyle? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign = TextAlign.Unspecified,
    lineHeight: TextUnit = TextUnit.Unspecified,
    style: TextStyle = TextStyle(),
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    inlineContent: Map<String, InlineTextContent> = mapOf()
) {
    var layoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }
    val isClickable = text.getStringAnnotations(0, text.length).find {
        it.tag == CLICKABLE_ANNOTATION_TAG || it.tag == LONG_CLICKABLE_ANNOTATION_TAG
    } != null

    val textColor = color.takeOrElse {
        style.color.takeOrElse {
            Color.White
        }
    }

    val textStyle = style.merge(
        TextStyle(
            color = textColor,
            fontSize = fontSize,
            fontWeight = fontWeight,
            textAlign = textAlign,
            lineHeight = lineHeight,
            fontFamily = fontFamily,
            textDecoration = textDecoration,
            fontStyle = fontStyle,
            letterSpacing = letterSpacing
        )
    )

    BasicText(
        text = text,
        modifier = modifier.thenIf(isClickable) {
            clickableText(text, layoutResult)
        },
        style = textStyle,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        inlineContent = inlineContent,
        onTextLayout = {
            layoutResult = it
        }
    )
}

/**
 * Adds support for handling clickable text done with [AnnotatedStyledTextBuilder][xyz.wingio.syntakts.compose.style.AnnotatedStyledTextBuilder]
 *
 * @param annotatedString Rendered text, needed to get the annotations
 * @param textLayoutResult Required for properly handling annotations
 */
@Composable
public fun Modifier.clickableText(annotatedString: AnnotatedString, textLayoutResult: TextLayoutResult?): Modifier {
    fun handleAnnotation(offset: Offset, annotationTag: String) {
        val pos = textLayoutResult?.getOffsetForPosition(offset) ?: return
        annotatedString
            .getStringAnnotations(pos, pos)
            .filter { it.tag == annotationTag }
            .forEach {
                ClickHandlerStore[it.item]?.invoke()
            }
    }

    return pointerInput(textLayoutResult) {
        if(textLayoutResult != null) {
            detectTapGestures(
                onTap = { offset ->
                    handleAnnotation(offset, CLICKABLE_ANNOTATION_TAG)
                },
                onLongPress = { offset ->
                    handleAnnotation(offset, LONG_CLICKABLE_ANNOTATION_TAG)
                }
            )
        }
    }
}

internal inline fun Modifier.thenIf(predicate: Boolean, block: Modifier.() -> Modifier): Modifier {
    return if (predicate) then(block()) else this
}