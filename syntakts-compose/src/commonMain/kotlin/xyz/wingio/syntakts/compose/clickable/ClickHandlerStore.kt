package xyz.wingio.syntakts.compose.clickable

import xyz.wingio.syntakts.compose.style.CLICKABLE_ID_SEPARATOR

/**
 * Stores handlers for clickable annotations, this is only needed due to how compose handles text clicking
 */
public object ClickHandlerStore {

    private val items: MutableMap</* builder id */ String, MutableMap</* handler id */ String, /* OnClick or OnLongClick */ () -> Unit>> = mutableMapOf()

    /**
     * Gets the handler for a given [id]
     *
     * @param id The id for the desired handler, must be two UUIDv4 strings separated by "||"
     */
    public operator fun get(id: String): (() -> Unit)? {
        if (!id.contains(CLICKABLE_ID_SEPARATOR)) return null
        val (builderId, handlerId) = id.split(CLICKABLE_ID_SEPARATOR, limit = 2)
        return items[builderId]?.get(handlerId)
    }

    /**
     * Adds a handler with a given [builderId] and [handlerId]
     *
     * @param builderId The id of the [AnnotatedStyledTextBuilder][xyz.wingio.syntakts.compose.style.AnnotatedStyledTextBuilder] that the clickable text comes from
     * @param handlerId The id of the specific click handler
     * @param handler Function to be called when specified text is clicked
     */
    public fun add(builderId: String, handlerId: String, handler: () -> Unit) {
        if(items.containsKey(builderId)) {
            items[builderId]!![handlerId] = handler
        } else {
            items[builderId] = mutableMapOf(handlerId to handler)
        }
    }

    /**
     * Clear all handlers for a builder
     *
     * @param builderId The id of the builder
     */
    public fun clearForBuilder(builderId: String) {
        items.remove(builderId)
    }

}