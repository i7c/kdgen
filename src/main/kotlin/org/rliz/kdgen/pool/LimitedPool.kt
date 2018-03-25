package org.rliz.kdgen.pool

import org.rliz.kdgen.data.LazyValue

class LimitedPool<out T : Any>(private val max: Int, private val backing: Pool<T>) : Pool<T> by backing {

    override fun getNew(): LazyValue<T> =
            if (size() >= max) throw IllegalStateException("Cannot get new element on full limited pool of size ${max}")
            else backing.getNew()
}

infix fun <T : Any, P : Pool<T>> P.limit(max: Int) = LimitedPool(max, this)
