package xyz.wingio.syntakts.node

import xyz.wingio.syntakts.parser.ParseSpec
import xyz.wingio.syntakts.style.StyledTextBuilder

/**
 * Creates a node that can contain children
 *
 * @param startIndex (inclusive) Where in the match to start parsing for children
 * @param endIndex (exclusive) Where in the match to stop parsing for children
 * @param renderer How to render the node
 * @return [ParseSpec] Describes how the [Node] should be processed
 */
public fun <C> nonTerminalNode(startIndex: Int, endIndex: Int, renderer: StyledTextBuilder<*>.(context: C) -> Unit): ParseSpec<C> {
    return ParseSpec.createNonterminal(
        root = object : Node<C>() {
            override fun render(builder: StyledTextBuilder<*>, context: C) {
                builder.renderer(context)
            }
        },
        startIndex = startIndex,
        endIndex = endIndex
    )
}

/**
 * Creates a node that can contain children
 *
 * @param range (inclusive start, exclusive end) Where in the match to look for children
 * @param renderer How to render the node
 * @return [ParseSpec] Describes how the [Node] should be processed
 */
public fun <C> nonTerminalNode(range: IntRange, renderer: StyledTextBuilder<*>.(context: C) -> Unit): ParseSpec<C>
    = nonTerminalNode(range.first, range.last + 1, renderer)

/**
 * Creates a node that can contain children
 *
 * @param startIndex (inclusive) Where in the match to start parsing for children
 * @param endIndex (exclusive) Where in the match to stop parsing for children
 * @param renderer How to render the node
 * @return [ParseSpec] Describes how the [Node] should be processed
 */
public fun <C> nodeWithChildren(startIndex: Int, endIndex: Int, renderer: StyledTextBuilder<*>.(context: C) -> Unit): ParseSpec<C>
    = nonTerminalNode(startIndex, endIndex, renderer)

/**
 * Creates a node that can contain children
 *
 * @param range (inclusive start, exclusive end) Where in the match to look for children
 * @param renderer How to render the node
 * @return [ParseSpec] Describes how the [Node] should be processed
 */
public fun <C> nodeWithChildren(range: IntRange, renderer: StyledTextBuilder<*>.(context: C) -> Unit): ParseSpec<C>
        = nonTerminalNode(range, renderer)

/**
 * Creates a node that does not contain children
 *
 * @see [node]
 *
 * @param renderer How to render the node
 * @return [ParseSpec] Describes how the [Node] should be processed
 */
public fun <C> terminalNode(renderer: StyledTextBuilder<*>.(context: C) -> Unit): ParseSpec<C> {
    return ParseSpec.createTerminal(
        root = object : Node<C>() {
            override fun render(builder: StyledTextBuilder<*>, context: C) {
                builder.renderer(context)
            }
        }
    )
}

/**
 * Creates a node that does not contain children
 *
 * @param renderer How to render the node
 * @return [ParseSpec] Describes how the [Node] should be processed
 */
public fun <C> node(renderer: StyledTextBuilder<*>.(context: C) -> Unit): ParseSpec<C>
    = terminalNode(renderer)