package org.rliz.kdgen.data.simple

import org.rliz.kdgen.common.resourceFileLines
import org.rliz.kdgen.data.LazyValue
import org.rliz.kdgen.data.primitive.CountingInt
import org.rliz.kdgen.state.Counter

class Country(private val countingInt: CountingInt = CountingInt(c)) : LazyValue<String> {

    override fun eval(): String = countries[countingInt.eval() % countries.size]

    companion object {
        private val c = Counter()

        private val countries = resourceFileLines("data/countries.txt")
    }
}
