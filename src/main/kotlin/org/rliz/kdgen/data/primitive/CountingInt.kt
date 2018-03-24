package org.rliz.kdgen.data.primitive

import org.rliz.kdgen.state.Counter

class CountingInt(counter: Counter = c) : LazyExpression<Int>({ counter.next() }) {

    companion object {
        private val c = Counter()
    }
}
