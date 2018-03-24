package org.rliz.kdgen.data.primitive

import org.rliz.kdgen.data.LazyValue
import org.rliz.kdgen.state.Counter

class AlternatingBoolean(private val countingInt: CountingInt = CountingInt(c)) : LazyValue<Boolean> {

    companion object {
        private val c = Counter()
    }

    override fun eval(): Boolean = countingInt.eval() % 2 == 0
}
