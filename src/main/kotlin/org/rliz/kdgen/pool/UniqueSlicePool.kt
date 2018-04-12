package org.rliz.kdgen.pool

import org.rliz.kdgen.data.LazyValue
import java.util.*

class UniqueSlicePool<T : Any>(
        private val maxDistributionFactor: Int,
        private val backing: Pool<T>,
        private val initialSlices: Int = 0
) : Pool<T> by backing {

    private val slice: MutableList<LinkedList<LazyValue<T>>> = MutableList(initialSlices, { LinkedList<LazyValue<T>>() })
    private var nextSlice = 0
    private var nextOfferedSlice = 0

    fun get(key: Int): LazyValue<T> = slice[key].let {
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

    private fun movePointer() {
        nextSlice = (nextSlice + 1) % slice.size
    }

    fun slice(): Int = if (nextOfferedSlice < slice.size) nextOfferedSlice++
    else {
        slice.add(LinkedList())
        nextOfferedSlice = slice.size
        slice.size - 1
    }
}

infix fun <T : Any> Pool<T>.uniqueSlices(maxDistributionFactor: Int) =
        UniqueSlicePool(maxDistributionFactor, this)

fun <T : Any> Pool<T>.uniqueSlices(maxDistributionFactor: Int, initialSize: Int) =
        UniqueSlicePool(maxDistributionFactor, this, initialSize)
