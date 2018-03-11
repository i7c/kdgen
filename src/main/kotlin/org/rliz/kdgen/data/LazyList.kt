package org.rliz.kdgen.data

class LazyList<T : Any> : List<T>, LazyValue<List<T>> {

    private val backing: List<LazyValue<T>>

    constructor() {
        backing = emptyList()
    }

    constructor(vararg v: LazyValue<T>) {
        backing = v.toList()
    }

    override val size: Int
        get() = backing.size

    override fun contains(element: T): Boolean = backing.map(LazyValue<T>::eval).filter(element::equals).isNotEmpty()

    override fun containsAll(elements: Collection<T>): Boolean = elements.map(this::contains)
            .reduce { acc, b -> acc && b }

    override fun get(index: Int): T = backing.get(index).eval()

    override fun indexOf(element: T): Int = backing.map(LazyValue<T>::eval).indexOf(element)

    override fun isEmpty(): Boolean = backing.isEmpty()

    override fun iterator(): Iterator<T> = backing.map(LazyValue<T>::eval).iterator()

    override fun lastIndexOf(element: T): Int = backing.map(LazyValue<T>::eval).lastIndexOf(element)

    override fun listIterator(): ListIterator<T> = backing.map(LazyValue<T>::eval).listIterator()

    override fun listIterator(index: Int): ListIterator<T> = backing.map(LazyValue<T>::eval).listIterator(index)

    override fun subList(fromIndex: Int, toIndex: Int): List<T> = backing.subList(fromIndex, toIndex).map(LazyValue<T>::eval)

    override fun eval(): List<T> = backing.map(LazyValue<T>::eval).toList()
}
