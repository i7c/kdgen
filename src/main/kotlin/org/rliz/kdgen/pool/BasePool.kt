package org.rliz.kdgen.pool

import com.fasterxml.jackson.annotation.JsonIgnoreType
import org.rliz.kdgen.data.LazyValue
import org.rliz.kdgen.data.primitive.LazyExpression
import org.rliz.kdgen.sink.DiscardSink
import org.rliz.kdgen.sink.Sink

@JsonIgnoreType
open class BasePool<out T : Any>(private val factory: () -> T, vararg sinks: Sink) : Pool<T> {
    private var last: LazyValue<T>? = null

    private var sealed = false
    private var sinks = sinks
    private var size = 0
    override fun get(): LazyValue<T> = getNew()

    override fun getNew(): LazyValue<T> {
        val t = safeProduceElement()
        last = t
        writeOut(t)
        return t
    }

    override fun getAnyExisting(): LazyValue<T> = if (last != null) {
        guard()
        last!!
    } else throw RuntimeException("Cannot get existing element on empty pool.")

    override fun size(): Int = size

    override fun nonEmpty() = size() > 0

    override fun empty() = !nonEmpty()

    private fun guard() {
        if (sealed) throw RuntimeException("Cannot read a sealed pool. Have you accessed a sealed pool from deferred operations such as 'transform'?")
    }

    private fun writeOut(t: LazyExpression<T>) {
        sinks.forEach { s -> s.write(t) }
    }

    private fun safeProduceElement(): LazyExpression<T> {
        guard()
        size++
        return LazyExpression({ factory() })
    }
}

fun <T : Any> pool(fws: FactoryWithSink<T>) = BasePool(fws.factory, fws.sink)
fun <T : Any> pool(factory: () -> T) = BasePool(factory)
fun <T : Any> root(factory: () -> T): LazyValue<T> {
    val rootPool = pool(factory pourInto DiscardSink())
    return rootPool.getNew()
}