package org.rliz.kdgen.data.primitive

import org.rliz.kdgen.state.Counter

class Modulo<out T : Any>(private val base: List<T>,
                          private val countingInt: CountingInt = CountingInt(c))
    : LazyExpression<T>({ base[countingInt.eval() % base.size] }) {

    companion object {
        private val c = Counter()
    }
}

fun <I : Any> List<I>.cycle(counter: Counter = Counter()) = { Modulo(this, CountingInt(counter)) }
