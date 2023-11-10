package xyz.wingio.syntakts.node

import androidx.compose.runtime.Stable
import xyz.wingio.syntakts.parser.ParseSpec
import xyz.wingio.syntakts.style.Style
import xyz.wingio.syntakts.style.StyledTextBuilder

/**
 * Node that applies a [style] to its children
 *
 * @param style The style to apply
 */
@Stable
public open class StyleNode<C>(
    public val style: Style
): Node.Parent<C>() {
    override fun render(builder: StyledTextBuilder<*>, context: C) {
        val startIndex = builder.length
        super.render(builder, context)

        builder.addStyle(style, startIndex, builder.length)
    }
}

/**
 * Creates a simple [Node] that applies a [style] to its children
 *
 * @param style The style to apply
 * @param range (inclusive start, exclusive end) Where this style should apply
 */
public fun <C> styleNode(style: Style, range: IntRange): ParseSpec<C> {
    return ParseSpec.createNonterminal(StyleNode(style), range.first, range.last + 1)
}

/**
 * Creates a simple [Node] that applies a [style] to its children
 *
 * @param style The style to apply
 * @param startIndex (inclusive) Where the style should start
 * @param endIndex (exclusive) Where the style should end
 */
public fun <C> styleNode(style: Style, startIndex: Int, endIndex: Int): ParseSpec<C> {
    return ParseSpec.createNonterminal(StyleNode(style), startIndex, endIndex)
}