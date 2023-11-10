package xyz.wingio.syntakts.node

import androidx.compose.runtime.Stable
import xyz.wingio.syntakts.parser.ParseSpec
import xyz.wingio.syntakts.style.StyledTextBuilder

/**
 * [Node] that only appends plain text
 *
 * @param content The text to append
 */
@Stable
public open class TextNode<C>(
    public val content: CharSequence
): Node<C>() {
    override fun render(builder: StyledTextBuilder<*>, context: C) {
        builder.append(content)
    }
}

/**
 * Creates a simple [Node] that only appends some text
 *
 * @param string The text to append
 */
public fun <C> textNode(string: CharSequence): ParseSpec<C> {
    return ParseSpec.createTerminal(TextNode(string))
}