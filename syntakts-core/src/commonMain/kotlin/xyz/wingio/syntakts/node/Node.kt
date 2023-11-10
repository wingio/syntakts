package xyz.wingio.syntakts.node

import androidx.compose.runtime.Stable
import xyz.wingio.syntakts.style.StyledTextBuilder

/**
 * Represents an AST node, this is what gets rendered
 *
 * @param _children Default child nodes
 * @param C (Context) Extra information to be used when rendering
 */
@Stable
public open class Node<C>(
    private var _children: MutableCollection<Node<C>>? = null
) {

    public val children: Collection<Node<C>>?
        get() = _children

    public val hasChildren: Boolean
        get() = children?.isNotEmpty() == true

    public open fun addChild(child: Node<C>) {
        _children = (_children ?: mutableListOf()).apply { add(child) }
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