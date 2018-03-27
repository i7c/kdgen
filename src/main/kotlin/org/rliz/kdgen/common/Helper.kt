package org.rliz.kdgen.common

import org.apache.commons.io.IOUtils

class Helper

infix fun <T : Any> Int.times(generator: () -> T): List<T> = (1..this).map { generator() }.toList()

fun resourceFileLines(path: String): List<String> =
        (IOUtils.readLines(Helper::class.java.classLoader.getResourceAsStream(path)) as List<String>?)!!
