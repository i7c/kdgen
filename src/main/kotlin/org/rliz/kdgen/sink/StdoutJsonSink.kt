package org.rliz.kdgen.sink

import org.rliz.kdgen.mapping.mapper

class StdoutJsonSink(private val prefix: String = "") : Sink() {

    override fun write(s: Any) {
        println(prefix + mapper.writeValueAsString(s))
    }
}

