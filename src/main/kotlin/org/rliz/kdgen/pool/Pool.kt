package org.rliz.kdgen.pool

import org.rliz.kdgen.data.LazyValue

interface Pool<T : Any> {

    /**
     * Obtain an item from the pool. Depending on the pool this might be a new item, an existing
     * item, the operation might throw an exception if you violate pool constraints but it will
     * never return "null".
     */
    fun get(): LazyValue<T>

    /**
     * Place an additional item in the pool. Pools do not have to enforce their constraints via
     * push and push is not meant to be called by users, unless they know what they are doing.
     */
    fun push(t: LazyValue<T>): LazyValue<T>

    /**
     * return the size of the pool
     */
    fun size(): Int

    fun nonEmpty(): Boolean

    fun empty(): Boolean
}
