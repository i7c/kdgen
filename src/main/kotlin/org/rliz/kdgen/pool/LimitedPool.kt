package org.rliz.kdgen.pool

import org.rliz.kdgen.data.LazyValue
import org.rliz.kdgen.sink.Sink

class LimitedPool<T : Any>(factory: () -> T,
                           private val min: Int,
                           private val max: Int,
                           vararg sinks: Sink)
    : BasePool<T>(factory, *sinks) {

    init {
        for (i in 1..min) getNew()
    }

    override fun getNew(): LazyValue<T> {
        limitGuard()
        return super.getNew()
    }

    override fun getNewAndSeal(): LazyValue<T> {
        limitGuard()
        return super.getNewAndSeal()
    }

    private fun limitGuard() {
        if (size() >= max) throw IllegalStateException("Cannot get new element on full limited pool of size $max")
    }

}