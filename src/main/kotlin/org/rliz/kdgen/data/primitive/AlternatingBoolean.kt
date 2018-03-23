package org.rliz.kdgen.data.primitive

import org.rliz.kdgen.data.LazyValue

class AlternatingBoolean(private val countingInt: CountingInt = CountingInt()) : LazyValue<Boolean> {

    override fun eval(): Boolean = countingInt.eval() % 2 == 0
}
