package org.rliz.kdgen.pool

import org.rliz.kdgen.data.LazyValue

interface Superpool<in L : Any, T : Any> {

    fun pool(): Pool<T>

    fun pool(lease: L): Pool<T>

    fun get(lease: L): LazyValue<T>
}
