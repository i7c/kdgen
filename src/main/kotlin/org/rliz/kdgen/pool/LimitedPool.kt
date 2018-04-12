package org.rliz.kdgen.pool

import org.rliz.kdgen.data.LazyValue

class LimitedPool<T : Any>(
    private val max: Int,
    private val backing: Pool<T>
) : Pool<T> by backing {

    override fun get(): LazyValue<T> =
        if (size() >= max) throw IllegalStateException("Cannot get new element on full limited pool of size ${max}")
        else backing.get()
}

infix fun <T : Any, P : Pool<T>> P.limit(max: Int) = LimitedPool(max, this)
