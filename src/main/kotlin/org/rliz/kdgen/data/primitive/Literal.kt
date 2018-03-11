package org.rliz.kdgen.data.primitive

import org.rliz.kdgen.data.LazyValue

class Literal<out T : Any>(private val t: T) : LazyValue<T> {

    override fun eval(): T = t

}
