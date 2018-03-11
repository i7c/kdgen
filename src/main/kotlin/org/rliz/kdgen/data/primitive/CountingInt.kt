package org.rliz.kdgen.data.primitive

import org.rliz.kdgen.state.Counter

class CountingInt(counter: Counter = Counter.singletion) : LazyExpression<Int>({ counter() })
