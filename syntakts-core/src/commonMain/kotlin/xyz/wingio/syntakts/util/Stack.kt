package xyz.wingio.syntakts.util


/**
 * Wrapper around MutableList that lets us use it as a stack, where you can push and pop elements
 */
internal class Stack<E>(private val items: MutableList<E> = mutableListOf()) : MutableList<E> by items {

    fun push(item: E): E {
        add(item)
        return item
    }

    fun pop(): E {
        return removeLast()
    }

}