package org.rliz.kdgen.data.simple

import org.rliz.kdgen.common.getResourceFile
import org.rliz.kdgen.data.LazyValue
import org.rliz.kdgen.data.primitive.CountingInt
import org.rliz.kdgen.state.Counter

class City(private val countingInt: CountingInt = CountingInt(c)) : LazyValue<String> {

    override fun eval(): String = cities[countingInt.eval() % cities.size]

    companion object {
        private val c = Counter()

        private val cities = getResourceFile("data/cities.txt").readLines()
    }
}