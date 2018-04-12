package org.rliz.kdgen.sink

import org.rliz.kdgen.mapping.mapper
import java.io.File

class FileJsonSink(val file: File) : Sink() {

    constructor(path: String) : this(File(path))

    override fun write(s: Any) = file.appendText("${mapper.writeValueAsString(s)}\n")
}
