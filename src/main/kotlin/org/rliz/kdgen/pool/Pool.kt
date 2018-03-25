package org.rliz.kdgen.pool

import org.rliz.kdgen.data.LazyValue

interface Pool<out T : Any> {

    /**
     * get any item, new or existing.
     */
    fun get(): LazyValue<T>

    /**
     * get new item.
     */
    fun getNew(): LazyValue<T>

    /**
     * returns any existing element.
     */
    fun getAnyExisting(): LazyValue<T>

    /**
     * return the size of the pool
     */
    fun size(): Int

    fun nonEmpty(): Boolean

    fun empty(): Boolean
}