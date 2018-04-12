package org.rliz.kdgen.pool

import com.fasterxml.jackson.annotation.JsonIgnoreType
import org.rliz.kdgen.data.LazyValue
import org.rliz.kdgen.data.primitive.LazyExpression
import org.rliz.kdgen.sink.DiscardSink
import org.rliz.kdgen.sink.Sink

@JsonIgnoreType
open class BasePool<T : Any>(private val factory: () -> T, vararg sinks: Sink) : Pool<T> {

    private var sealed = false
    private var sinks = sinks
    private var size = 0

    override fun get(): LazyValue<T> {
        val t = LazyExpression({ factory() })
        return placeInPool(t)
    }

    override fun push(t: LazyValue<T>) = placeInPool(t)

    fun placeInPool(t: LazyValue<T>): LazyValue<T> {
        size++
        writeOut(t)
        return t
    }

    override fun size(): Int = size

    override fun nonEmpty() = size() > 0

    override fun empty() = !nonEmpty()

    private fun writeOut(t: LazyValue<T>) {
        sinks.forEach { s -> s.write(t) }
    }
}

fun <T : Any> pool(fws: FactoryWithSink<T>) = BasePool(fws.factory, fws.sink)
fun <T : Any> pool(factory: () -> T) = BasePool(factory)
fun <T : Any> root(factory: () -> T): LazyValue<T> = pool(factory pourInto DiscardSink()).get()
