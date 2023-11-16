package xyz.wingio.syntakts.node

import androidx.compose.runtime.Stable
import xyz.wingio.syntakts.style.StyledTextBuilder

/**
 * Represents an AST node, this is what gets rendered
 *
 * @param _children Default child nodes
 * @param _metadata Metadata associated with this node, only present when the storeMetadata [debug option][xyz.wingio.syntakts.Syntakts.DebugOptions] is enabled
 * @param C (Context) Extra information to be used when rendering
 */
@Stable
public open class Node<C>(
    private var _children: MutableCollection<Node<C>>? = null,
    private var _metadata: MetaData? = null
) {

    /**
     * Information to be stored alongside a [Node]
     *
     * @param ruleName Name of the rule that created this node
     * @param rule The pattern used to create this node
     * @param matchResult The matched text for this node
     */
    public data class MetaData(
        val ruleName: String,
        val rule: Regex,
        val matchResult: MatchResult
    )

    /**
     * Metadata associated with this node, only present when the storeMetadata [debug option][xyz.wingio.syntakts.Syntakts.DebugOptions] is enabled
     */
    public val metadata: MetaData?
        get() = _metadata

    /**
     * Child nodes
     */
    public val children: Collection<Node<C>>?
        get() = _children

    /**
     * Whether or not this node has any children
     */
    public val hasChildren: Boolean
        get() = children?.isNotEmpty() == true

    /**
     * Adds a child node
     *
     * @param child The new child node
     */
    public open fun addChild(child: Node<C>) {
        _children = (_children ?: mutableListOf()).apply { add(child) }
    }

    /**
     * Set node metadata
     *
     * @param ruleName Name of the rule that created this node
     * @param rule The pattern used to create this node
     * @param matchResult The matched text for this node
     */
    public open fun setMetadata(
        ruleName: String,
        rule: Regex,
        matchResult: MatchResult
    ) {
        _metadata = MetaData(ruleName, rule, matchResult)
    }

    /**
     * Render this node
     *
     * @param builder Builder used to append text and apply styles
     * @param context Additional information used to help render this node
     */
    public open fun render(builder: StyledTextBuilder<*>, context: C) {}

    /**
     * Represents a node that only renders its children
     *
     * @param children Default child nodes
     * @param C (Context) Extra information to be used when rendering
     */
    public open class Parent<C>(vararg children: Node<C>?) : Node<C>(children.mapNotNull { it }.toMutableList()) {
        override fun render(builder: StyledTextBuilder<*>, context: C) {
            children?.forEach { it.render(builder, context) }
        }
    }

}