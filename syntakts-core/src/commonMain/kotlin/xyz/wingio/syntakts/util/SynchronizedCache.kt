package xyz.wingio.syntakts.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * Thread safe cache store, all writes are restricted with a [Mutex] to prevent [ConcurrentModificationException]s
 */
public class SynchronizedCache<K, V> {

    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Main.immediate)
    private val mutex: Mutex = Mutex(false)
    private val cache: MutableMap<K, V?> = mutableMapOf()

    /**
     * Number of cached entities
     */
    public val size: Int get() = cache.size

    public operator fun set(key: K, value: V?) {
        scope.launch {
            mutex.withLock {
                cache[key] = value
            }
        }
    }

    public operator fun get(key: K): V? {
        return cache[key]
    }

    /**
     * Returns true if this cache already contains an element with the given [key]
     */
    public fun hasKey(key: K): Boolean {
        return cache.containsKey(key)
    }

    /**
     * Removes the first element of this cache
     */
    public fun removeFirst() {
        scope.launch {
            mutex.withLock {
                cache.remove(cache.keys.firstOrNull())
            }
        }
    }

}