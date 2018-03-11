package org.rliz.kdgen.pool

import org.rliz.kdgen.data.LazyValue

interface Pool<out T : Any> {

    fun get(): LazyValue<T>

    fun getNew(): LazyValue<T>

    fun getNewAndSeal(): LazyValue<T>

    fun getAnyExisting(): LazyValue<T>

    fun seal(): Unit

    fun size(): Int

}