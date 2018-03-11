package org.rliz.kdgen.data.primitive

import org.rliz.kdgen.data.LazyValue

open class LazyExpression<out T : Any>(private val expression: () -> T) : LazyValue<T> {

    private var actual: T? = null

    override fun eval(): T = actual ?: {
        val x = expression()
        actual = x
        x
    }()

}

fun <I : Any> I.lazify(): LazyValue<I> = LazyExpression({ this })
