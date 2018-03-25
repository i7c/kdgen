package org.rliz.kdgen.data.simple

import org.rliz.kdgen.data.LazyValue
import org.rliz.kdgen.data.primitive.AlternatingBoolean
import org.rliz.kdgen.data.primitive.CountingInt
import org.rliz.kdgen.data.primitive.cycle
import org.rliz.kdgen.state.Counter
import java.util.concurrent.atomic.AtomicInteger

class HouseNumber(private val ci: CountingInt = CountingInt(c)) : LazyValue<Int> {

    companion object {
        private val c = Counter(AtomicInteger(17))
    }

    override fun eval(): Int = ci.eval().let { it * 271 % 999 + 1 }
}

class ExtendedHouseNumber : LazyValue<String> {

    companion object {
        private val toggleCounter = Counter()
        private val supplementCounter = Counter()
        private val supplementSource = listOf("/2", "/3", " A", " B").cycle(supplementCounter)
    }

    private val hn = HouseNumber()
    private val toggle = AlternatingBoolean(CountingInt(toggleCounter))
    private val supplement = supplementSource()

    override fun eval(): String =
            if (toggle.eval()) hn.eval().toString()
            else hn.eval().let { "$it${supplement.eval()}" }
}