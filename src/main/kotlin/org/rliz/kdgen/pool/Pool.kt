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
     * same as get, but seals to pool directly afterwards.
     */
    fun getNewAndSeal(): LazyValue<T>

    /**
     * returns any existing element.
     */
    fun getAnyExisting(): LazyValue<T>

    /**
     * seal the pool, no more items can be added.
     */
    fun seal(): Unit

    /**
     * return the size of the pool
     */
    fun size(): Int

    fun nonEmpty(): Boolean

    fun empty(): Boolean
}