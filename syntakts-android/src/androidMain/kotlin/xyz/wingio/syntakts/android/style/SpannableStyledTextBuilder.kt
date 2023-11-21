package xyz.wingio.syntakts.android.style

import android.content.Context
import android.text.Spannable
import android.text.SpannableStringBuilder
import xyz.wingio.syntakts.android.spans.ClickableSpan
import xyz.wingio.syntakts.android.spans.SyntaktsStyleSpan
import xyz.wingio.syntakts.style.Style
import xyz.wingio.syntakts.style.StyledTextBuilder

public class SpannableStyledTextBuilder(
    public val context: Context
) : StyledTextBuilder<CharSequence> {
    private val builder = SpannableStringBuilder()

    override val length: Int
        get() = builder.length

    override fun append(text: CharSequence, style: Style?): SpannableStyledTextBuilder {
        val i = length
        builder.append(text)
        style?.let {
            builder.setSpan(
                /* what = */ SyntaktsStyleSpan(style, context),
                /* start = */ i,
                /* end = */ length,
                /* flags = */ Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            it.paragraphStyle?.let { paragraphStyle ->
                paragraphStyle.lineHeight
            }
        }
        return this
    }

    override fun append(
        text: CharSequence,
        style: Style.() -> Unit
    ): SpannableStyledTextBuilder {
        return append(text = text, style = Style().apply(style))
    }

    override fun appendClickable(
        text: CharSequence,
        style: Style?,
        onLongClick: (() -> Unit)?,
        onClick: () -> Unit
    ): SpannableStyledTextBuilder {
        val i = length
        append(text, style)
        builder.setSpan(
            /* what = */ ClickableSpan(
                onClickListener = onClick,
                onLongClickListener = onLongClick
            ),
            /* start = */ i,
            /* end = */ length,
            /* flags = */ Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return this
    }

    override fun addClickable(
        startIndex: Int,
        endIndex: Int,
        onLongClick: (() -> Unit)?,
        onClick: () -> Unit
    ): SpannableStyledTextBuilder {
        builder.setSpan(
            /* what = */ ClickableSpan(
                onClickListener = onClick,
                onLongClickListener = onLongClick
            ),
            /* start = */ startIndex,
            /* end = */ endIndex,
            /* flags = */ Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return this
    }

    override fun addStyle(
        style: Style,
        startIndex: Int,
        endIndex: Int
    ): SpannableStyledTextBuilder {
        builder.setSpan(
            /* what = */ SyntaktsStyleSpan(style, context),
            /* start = */ startIndex,
            /* end = */ endIndex,
            /* flags = */ Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return this
    }

    override fun addStyle(
        startIndex: Int,
        endIndex: Int,
        style: Style.() -> Unit
    ): SpannableStyledTextBuilder {
        builder.setSpan(
            /* what = */ SyntaktsStyleSpan(Style().apply(style), context),
            /* start = */ startIndex,
            /* end = */ endIndex,
            /* flags = */ Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return this
    }

    override fun clear() {
        builder.clear()
    }

    override fun build(): CharSequence {
        return builder
    }

}