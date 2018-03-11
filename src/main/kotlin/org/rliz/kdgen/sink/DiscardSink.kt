package org.rliz.kdgen.sink

import org.rliz.kdgen.mapping.mapper

class DiscardSink : Sink() {

    override fun write(s: Any) {
        mapper.writeValueAsString(s)
    }

}
