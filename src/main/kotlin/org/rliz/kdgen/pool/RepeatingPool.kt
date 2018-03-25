package org.rliz.kdgen.pool

import org.rliz.kdgen.data.LazyValue
import org.rliz.kdgen.sink.Sink

class RepeatingPool<out T : Any>(
        private val repetitions: Int,
        private val factory: () -> T,
        vararg sinks: Sink
) : BasePool<T>(factory, *sinks) {

    private var currentReps = 0

    override fun get(): LazyValue<T> =
            if ((currentReps++) < repetitions && nonEmpty()) getAnyExisting() else getNew()

    override fun getNew(): LazyValue<T> {
        currentReps = 1
        return super.getNew()
    }
}

fun <T : Any> repeatPool(repetitions: Int, factory: () -> T) = RepeatingPool(repetitions, factory)
fun <T : Any> repeatPool(repetitions: Int, fws: FactoryWithSink<T>) = RepeatingPool(repetitions, fws.factory, fws.sink)
