package org.rliz.kdgen.data

import org.rliz.kdgen.data.primitive.LazyExpression

interface LazyValue<out T : Any> {

    fun eval(): T

    infix fun <O : Any> transform(transformation: (T) -> O): LazyValue<O> =
            LazyExpression({ transformation(this.eval()) })

    infix fun <O : Any> flatTransform(transformation: (T) -> LazyValue<O>) =
            LazyExpression({ transformation(this.eval()).eval() })

    infix fun <I2 : Any> with(that: LazyValue<I2>) = Pair(this, that)

    @Deprecated("Experimental. Might be removed.", replaceWith = ReplaceWith("eval()"))
    operator fun unaryPlus(): T = eval()
}

infix fun <I1 : Any, I2 : Any, O : Any> Pair<LazyValue<I1>, LazyValue<I2>>.transform(transformation: (I1, I2) -> O) =
        LazyExpression({ transformation(this.first.eval(), this.second.eval()) })
