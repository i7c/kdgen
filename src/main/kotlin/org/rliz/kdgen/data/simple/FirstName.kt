package org.rliz.kdgen.data.simple

import org.rliz.kdgen.data.primitive.AlternatingBoolean
import org.rliz.kdgen.data.primitive.CountingInt
import org.rliz.kdgen.data.primitive.LazyExpression
import org.rliz.kdgen.state.Counter

class FirstName : LazyExpression<String>({
    (if (AlternatingBoolean(CountingInt(c)).eval()) FemaleFirstName() else MaleFirstName()).eval()
}) {
    companion object {
        private val c = Counter()
    }
}
