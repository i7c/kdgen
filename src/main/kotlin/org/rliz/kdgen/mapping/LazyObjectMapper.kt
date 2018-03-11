package org.rliz.kdgen.mapping

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import org.rliz.kdgen.data.LazyValue

class LazyObjectMapper : StdSerializer<LazyValue<*>> {

    constructor() : super(LazyValue::class.java)

    override fun serialize(lv: LazyValue<*>?, gen: JsonGenerator?, provider: SerializerProvider?) {
        gen!!.writeObject(lv!!.eval())
    }
}
