package org.rliz.kdgen.data.primitive

import org.rliz.kdgen.data.LazyValue

infix fun <I : Any, O : Any> LazyValue<I>.transform(transformation: (I) -> O): LazyValue<O> =
        LazyExpression({ transformation(this.eval()) })

infix fun <I : Any, O : Any> LazyValue<I>.flatTransform(transformation: (I) -> LazyValue<O>) =
        LazyExpression({ transformation(this.eval()).eval() })
