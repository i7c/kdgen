package org.rliz.kdgen.pool

import org.rliz.kdgen.sink.Sink

data class FactoryWithSink<out T : Any>(val factory: () -> T, val sink: Sink)

infix fun <T : Any> (() -> T).pourInto(s: Sink) = FactoryWithSink(this, s)