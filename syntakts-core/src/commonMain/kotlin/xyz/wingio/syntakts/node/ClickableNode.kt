package xyz.wingio.syntakts.node

import androidx.compose.runtime.Stable
import xyz.wingio.syntakts.parser.ParseSpec
import xyz.wingio.syntakts.style.StyledTextBuilder

/**
 * Node that marks its children as clickable
 *
 * @param onClick Callback for when the child nodes are clicked
 * @param onLongClick Callback for when the child nodes are long clicked
 */
@Stable
public class ClickableNode<C>(
    public val onClick: () -> Unit,
    public val onLongClick: (() -> Unit)? = null
): Node.Parent<C>() {
    override fun render(builder: StyledTextBuilder<*>, context: C) {
        val startIndex = builder.length
        super.render(builder, context)

        builder.addClickable(startIndex, builder.length, onLongClick, onClick)
    }
}

/**
 * Creates a simple [Node] that marks its children as clickable
 *
 * @param range (inclusive start, exclusive end) What part should be clickable
 * @param onLongClick Callback for when the child nodes are long clicked
 * @param onClick Method called when the children are clicked
 */
public fun <C> clickableNode(range: IntRange, onLongClick: (() -> Unit)? = null, onClick: () -> Unit): ParseSpec<C> {
    return ParseSpec.createNonterminal(ClickableNode(onClick, onLongClick), range.first, range.last + 1)
}

/**
 * Creates a simple [Node] that marks its children as clickable
 *
 * @param startIndex (inclusive) Where the clickable area should start
 * @param endIndex (exclusive) Where the clickable area should end
 * @param onLongClick Callback for when the child nodes are long clicked
 * @param onClick Method called when the children are clicked
 */
public fun <C> clickableNode(startIndex: Int, endIndex: Int, onLongClick: (() -> Unit)? = null, onClick: () -> Unit): ParseSpec<C> {
    return ParseSpec.createNonterminal(ClickableNode(onClick, onLongClick), startIndex, endIndex)
}