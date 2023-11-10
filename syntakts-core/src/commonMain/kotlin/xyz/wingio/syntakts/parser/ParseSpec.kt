package xyz.wingio.syntakts.parser

import androidx.compose.runtime.Stable
import xyz.wingio.syntakts.node.Node

/**
 * Tells the parser how to best parse a [Node]
 *
 * For nonterminal subtrees, the provided root will be added to the main, and text between
 * [startIndex] (inclusive) and [endIndex] (exclusive) will continue to be parsed into [Node]s and
 * added as children under this root.
 *
 *
 * For terminal subtrees, the root will simply be added to the tree and no additional parsing will
 * take place on the text.
 *
 * @param C The context used to pass additional information to a [Node] when rendering
 */
@Stable
public class ParseSpec<C> {

    public val root: Node<C>
    public val isTerminal: Boolean
    public var startIndex: Int = 0
    public var endIndex: Int = 0

    internal constructor(root: Node<C>, startIndex: Int, endIndex: Int) {
        this.root = root
        this.isTerminal = false
        this.startIndex = startIndex
        this.endIndex = endIndex
    }

    internal constructor(root: Node<C>) {
        this.root = root
        this.isTerminal = true
    }

    internal fun applyOffset(offset: Int) {
        startIndex += offset
        endIndex += offset
    }

    public companion object {

        /**
         * Creates a nonterminal [ParseSpec]
         *
         * @param root The starting node
         * @param startIndex (inclusive) Where in the match to start parsing for children
         * @param endIndex (exclusive) Where in the match to stop parsing for children
         * @return [ParseSpec] Describes how the [Node] should be processed
         */
        public fun <C> createNonterminal(root: Node<C>, startIndex: Int, endIndex: Int): ParseSpec<C> = ParseSpec(root, startIndex, endIndex)

        /**
         * Creates a terminal [ParseSpec]
         *
         * @param root The [Node] the [ParseSpec] will represent
         * @return [ParseSpec] Describes how the [Node] should be processed
         */
        public fun <C> createTerminal(root: Node<C>): ParseSpec<C> = ParseSpec(root)

    }

}

