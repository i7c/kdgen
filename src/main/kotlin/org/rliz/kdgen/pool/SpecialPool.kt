package org.rliz.kdgen.pool

import org.rliz.kdgen.data.LazyValue
import org.rliz.kdgen.data.primitive.LazyExpression

class SpecialPool<T : Any>(
        private val factory: () -> T,
        private val backing: Pool<T>
) : Pool<T> by backing {

    override fun get(): LazyValue<T> {
        return backing.push(LazyExpression({ factory() }))
    }
}

infix fun <T : Any> Pool<T>.special(factory: () -> T) = SpecialPool(factory, this)
