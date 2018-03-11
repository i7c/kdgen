package org.rliz.kdgen.data.primitive

private var lastAlternatingBoolean = false

class AlternatingBoolean : LazyExpression<Boolean>({
    lastAlternatingBoolean = !lastAlternatingBoolean
    lastAlternatingBoolean
})
