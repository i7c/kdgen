package org.rliz.kdgen.common

import java.io.File

infix fun <T : Any> Int.times(generator: () -> T): List<T> = (1..this).map { generator() }.toList()

fun getResourceFile(path: String) = File(ClassLoader.getSystemClassLoader().getResource(path).file)