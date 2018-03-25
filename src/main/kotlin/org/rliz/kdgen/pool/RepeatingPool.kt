package org.rliz.kdgen.pool

import org.rliz.kdgen.data.LazyValue

class RepeatingPool<out T : Any>(
        private val repetitions: Int,
        private val backing: Pool<T>
) : Pool<T> by backing {

    private var currentReps = 0

    override fun get(): LazyValue<T> =
            if ((currentReps++) < repetitions && nonEmpty()) getAnyExisting() else getNew()

    override fun getNew(): LazyValue<T> {
        currentReps = 1
        return backing.getNew()
    }
}

infix fun <T : Any, P : Pool<T>> P.repeating(repetitions: Int) = RepeatingPool(repetitions, this)
