package org.rliz.kdgen.data.primitive

import org.rliz.kdgen.state.Counter

class CountingString(prefix: String, countingInt: CountingInt = CountingInt(), suffix: String = "")
    : LazyExpression<String>({ prefix + countingInt.eval() + suffix })

infix fun String.countedBy(counter: Counter) = CountingString(this, counter.fix())