package org.rliz.kdgen.mapping

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import org.rliz.kdgen.data.LazyValue

val mapper = mapper()

fun mapper(): ObjectMapper {
    val mapper = ObjectMapper()
    val simpleModule = SimpleModule()
    simpleModule.addSerializer(LazyValue::class.java, LazyObjectMapper())
    mapper.registerModule(simpleModule)
    return mapper
}
