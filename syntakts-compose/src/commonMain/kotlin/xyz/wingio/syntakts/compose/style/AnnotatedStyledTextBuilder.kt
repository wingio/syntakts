package xyz.wingio.syntakts.compose.style

import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.LineHeightStyle
import com.benasher44.uuid.uuid4
import xyz.wingio.syntakts.compose.clickable.ClickHandlerStore
import xyz.wingio.syntakts.style.Style
import xyz.wingio.syntakts.style.StyledTextBuilder

internal const val CLICKABLE_ANNOTATION_TAG: String = "xyz.wingio.syntakts.clickable"
internal const val LONG_CLICKABLE_ANNOTATION_TAG: String = "xyz.wingio.syntakts.longclickable"
internal const val CLICKABLE_ID_SEPARATOR: String = "||"

/**
 * Instance of [StyledTextBuilder] that builds [AnnotatedString]s
 *
 * @param builder (Optional) Base [AnnotatedString.Builder] to use
 */
public class AnnotatedStyledTextBuilder(
    private var builder: AnnotatedString.Builder = AnnotatedString.Builder()
): StyledTextBuilder<AnnotatedString> {
    internal val id = uuid4().toString()

    /**
     * Instance of [StyledTextBuilder] that builds [AnnotatedString]s
     *
     * @param text Initial text
     */
    public constructor(text: String): this(AnnotatedString.Builder(text))

    override val length: Int
        get() = builder.length

    override fun append(text: CharSequence, style: Style?): AnnotatedStyledTextBuilder {
        val i = length
        builder.append(text)

        style?.let {
            builder.addStyle(it.toSpanStyle(), i, builder.length)
            it.applyParagraphStyle(i, builder.length)
        }
        return this
    }

    override fun append(text: CharSequence, style: Style.() -> Unit): AnnotatedStyledTextBuilder {
        return append(text, Style().apply(style))
    }

    override fun appendClickable(
        text: CharSequence,
        style: Style?,
        onLongClick: (() -> Unit)?,
        onClick: () -> Unit
    ): AnnotatedStyledTextBuilder {
        val i = length

        builder.append(text)

        // OnClick
        val onClickHandlerId = uuid4().toString()
        builder.addStringAnnotation(CLICKABLE_ANNOTATION_TAG, "$id$CLICKABLE_ID_SEPARATOR$onClickHandlerId", i, builder.length)
        ClickHandlerStore.add(id, onClickHandlerId, onClick)

        // OnLongClick
        onLongClick?.let {
            val onLongClickHandlerId = uuid4().toString()
            builder.addStringAnnotation(LONG_CLICKABLE_ANNOTATION_TAG, "$id$CLICKABLE_ID_SEPARATOR$onLongClickHandlerId", i, builder.length)
            ClickHandlerStore.add(id, onLongClickHandlerId, onLongClick)
        }

        style?.let {
            builder.addStyle(it.toSpanStyle(), i, builder.length)
            it.applyParagraphStyle(i, builder.length)
        }

        return this
    }

    override fun appendAnnotated(
        text: CharSequence,
        tag: String,
        annotation: String
    ): AnnotatedStyledTextBuilder {
        val i = length
        append(text)
        addAnnotation(tag, annotation, i, length)
        return this
    }

    override fun addClickable(
        startIndex: Int,
        endIndex: Int,
        onLongClick: (() -> Unit)?,
        onClick: () -> Unit
    ): StyledTextBuilder<AnnotatedString> {
        // OnClick
        val onClickHandlerId = uuid4().toString()
        builder.addStringAnnotation(CLICKABLE_ANNOTATION_TAG, "$id$CLICKABLE_ID_SEPARATOR$onClickHandlerId", startIndex, endIndex)
        ClickHandlerStore.add(id, onClickHandlerId, onClick)

        // OnLongClick
        onLongClick?.let {
            val onLongClickHandlerId = uuid4().toString()
            builder.addStringAnnotation(LONG_CLICKABLE_ANNOTATION_TAG, "$id$CLICKABLE_ID_SEPARATOR$onLongClickHandlerId", startIndex, endIndex)
            ClickHandlerStore.add(id, onLongClickHandlerId, onLongClick)
        }

        return this
    }

    override fun addStyle(style: Style, startIndex: Int, endIndex: Int): AnnotatedStyledTextBuilder {
        builder.addStyle(style.toSpanStyle(), startIndex, endIndex)
        style.applyParagraphStyle(startIndex, endIndex)
        return this
    }

    override fun addStyle(startIndex: Int, endIndex: Int, style: Style.() -> Unit): AnnotatedStyledTextBuilder {
        val _style = Style().apply(style)
        builder.addStyle(_style.toSpanStyle(), startIndex, endIndex)
        _style.applyParagraphStyle(startIndex, endIndex)
        return this
    }

    override fun addAnnotation(
        tag: String,
        annotation: String,
        startIndex: Int,
        endIndex: Int
    ): AnnotatedStyledTextBuilder {
        builder.addStringAnnotation(tag, annotation, startIndex, endIndex)
        return this
    }

    override fun clear() {
        builder = AnnotatedString.Builder()
    }

    override fun build(): AnnotatedString = builder.toAnnotatedString()

    private fun Style.toSpanStyle(): SpanStyle {
        return SpanStyle(
            color = color.toComposeColor(),
            fontWeight = fontWeight?.toComposeFontWeight(),
            fontStyle = fontStyle?.toComposeFontStyle(),
            background = background.toComposeColor(),
            fontSize = fontSize.toComposeTextUnit(),
            letterSpacing = letterSpacing.toComposeTextUnit(),
            textDecoration = textDecoration?.toComposeTextDecoration()
        )
    }

    private fun Style.applyParagraphStyle(startIndex: Int, endIndex: Int) {
        paragraphStyle?.run {
            builder.addStyle(
                ParagraphStyle(
                    lineHeight = lineHeight.toComposeTextUnit(),
                    lineBreak = LineBreak.Heading,
                    lineHeightStyle = LineHeightStyle(LineHeightStyle.Alignment.Center, LineHeightStyle.Trim.Both)
                ),
                startIndex,
                endIndex
            )
        }
    }

}

/**
 * Used to insert composables into the text layout
 *
 * @param id The id used to look up the [InlineTextContent]
 * @param alternateText The text to be replaced by the inline content.
 */
public fun <S> StyledTextBuilder<S>.appendInlineContent(
    id: String,
    alternateText: CharSequence = "\uFFFD"
): StyledTextBuilder<S> {
    appendAnnotated(
        text = alternateText,
        tag = "androidx.compose.foundation.text.inlineContent",
        annotation = id
    )
    return this
}

/**
 * Used to insert composables into the text layout, replaces any text within the [range]
 *
 * @see appendInlineContent
 *
 * @param id The id used to look up the [InlineTextContent]
 * @param range (inclusive start, exclusive end) Where the content will be placed
 */
public fun <S> StyledTextBuilder<S>.addInlineContent(
    id: String,
    range: IntRange
): StyledTextBuilder<S> = addInlineContent(id, range.first, range.last + 1)

/**
 * Used to insert composables into the text layout, replaces any text within the range of [startIndex] to [endIndex]]
 *
 * @see appendInlineContent
 *
 * @param id The id used to look up the [InlineTextContent]
 * @param startIndex (inclusive) Start of the replaced text
 * @param endIndex (exclusive) End of the replaced text
 */
public fun <S> StyledTextBuilder<S>.addInlineContent(
    id: String,
    startIndex: Int,
    endIndex: Int,
): StyledTextBuilder<S> {
    addAnnotation("androidx.compose.foundation.text.inlineContent", id, startIndex, endIndex)
    return this
}