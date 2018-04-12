package org.rliz.kdgen.pool

import org.rliz.kdgen.data.LazyValue
import java.util.LinkedList

@Deprecated("Experimental")
class DistinctSliceSuperpool<T : Any>(
    private val maxDistributionFactor: Int,
    private val backing: Pool<T>,
    private val initialSlices: Int = 0
) : Superpool<Int, T> {

    private val slice: MutableList<LinkedList<LazyValue<T>>> =
        MutableList(initialSlices, { LinkedList<LazyValue<T>>() })

    private var nextSlice = 0
    private var nextOfferedSlice = 0

    override fun pool(): Pool<T> = LeasePool(slice(), this)

    override fun pool(lease: Int): Pool<T> = LeasePool(lease, this)

    override fun get(key: Int): LazyValue<T> = slice[key].let {
        if (it.isNotEmpty()) it.poll()
        else {
            val next = backing.get()
            var i = Math.min(maxDistributionFactor, slice.size - 1)
            while (i > 0) {
                if (nextSlice == key) movePointer()
                slice[nextSlice].offer(next)
                movePointer()
                i--
            }
            next
        }
    }

    fun slice(): Int =
        if (nextOfferedSlice < slice.size) nextOfferedSlice++
        else {
            slice.add(LinkedList())
            nextOfferedSlice = slice.size
            slice.size - 1
        }

    private fun movePointer() {
        nextSlice = (nextSlice + 1) % slice.size
    }

    class LeasePool<T : Any>(
        private val lease: Int,
        private val backing: Superpool<Int, T>
    ) : Pool<T> {

        override fun push(t: LazyValue<T>): LazyValue<T> {
            throw IllegalArgumentException("Cannot push on a lease pool")
        }

        override fun size(): Int {
            throw IllegalArgumentException("Not allowed on a lease pool")
        }

        override fun nonEmpty(): Boolean {
            throw IllegalArgumentException("Not allowed on a lease pool")
        }

        override fun empty(): Boolean {
            throw IllegalArgumentException("Not allowed on a lease pool")
        }

        override fun get(): LazyValue<T> = backing.get(lease)
    }
}

infix fun <T : Any> Pool<T>.distinctSlices(maxDistributionFactor: Int) =
    DistinctSliceSuperpool(maxDistributionFactor, this)

fun <T : Any> Pool<T>.distinctSlices(maxDistributionFactor: Int, initialSize: Int) =
    DistinctSliceSuperpool(maxDistributionFactor, this, initialSize)
