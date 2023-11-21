package xyz.wingio.syntakts.android.markdown

import android.widget.TextView
import xyz.wingio.syntakts.android.render
import xyz.wingio.syntakts.markdown.BasicMarkdownSyntakts
import xyz.wingio.syntakts.markdown.MarkdownSyntakts

/**
 * Renders some Markdown to this [TextView]
 *
 * @param text The Markdown to be rendered, see [MarkdownSyntakts] for supported rules
 * @see [MarkdownSyntakts]
 */
public fun TextView.renderMarkdown(text: String): Unit = render(text, MarkdownSyntakts)

/**
 * Renders some basic Markdown rules to this [TextView]
 *
 * @param text The Markdown to be rendered, see [BasicMarkdownSyntakts] for supported rules
 * @see [BasicMarkdownSyntakts]
 */
public fun TextView.renderBasicMarkdown(text: String): Unit = render(text, BasicMarkdownSyntakts)