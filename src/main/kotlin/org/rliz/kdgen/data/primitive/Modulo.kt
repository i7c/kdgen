package org.rliz.kdgen.data.primitive

import org.rliz.kdgen.state.Counter

class Modulo<out T : Any>(private val base: List<T>,
                          private val countingInt: CountingInt = CountingInt())
    : LazyExpression<T>({ base[countingInt.eval() % base.size] })

fun <I : Any> List<I>.modulate(counter: Counter = Counter()) = { Modulo(this, CountingInt(counter)) }
