package org.rliz.kdgen.data.simple

import org.rliz.kdgen.data.primitive.AlternatingBoolean
import org.rliz.kdgen.data.primitive.LazyExpression

class FirstName : LazyExpression<String>({
    (if (AlternatingBoolean().eval()) FemaleFirstName() else MaleFirstName()).eval()
})
