package xyz.wingio.syntakts.util

internal inline fun <T, V> List<T>.firstMapOrNull(predicate: (T) -> V?): V? {
    for (element in this) {
        @Suppress("UnnecessaryVariable")  // wants to inline, but it's unreadable that way
        val found = predicate(element) ?: continue
        return found
    }
    return null
}