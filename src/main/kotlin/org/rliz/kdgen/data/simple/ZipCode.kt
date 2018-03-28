package org.rliz.kdgen.data.simple

import org.rliz.kdgen.data.primitive.LazyExpression
import org.rliz.kdgen.data.primitive.cycle
import org.rliz.kdgen.state.Counter
import java.util.*

class ZipCode : LazyExpression<String>({ "${zipCodeSource().eval()}" }) {
    companion object {
        private val zipCodeSource = (10000..99999).shuffled(Random(987321)).cycle(Counter())
    }
}