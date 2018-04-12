package org.rliz.kdgen.pool

import org.rliz.kdgen.data.LazyValue

class RepeatingPool<T : Any>(
        private val repetitions: Int,
        private val backing: Pool<T>
) : Pool<T> by backing {

    private var currentReps = 0
    private var last: LazyValue<T>? = null

    override fun get(): LazyValue<T> =
            if ((currentReps++) < repetitions && last != null) last!! else getNext()

    fun getNext(): LazyValue<T> {
        currentReps = 1
        val next = backing.get()
        last = next
        return next
    }
}

infix fun <T : Any, P : Pool<T>> P.repeating(repetitions: Int) = RepeatingPool(repetitions, this)
