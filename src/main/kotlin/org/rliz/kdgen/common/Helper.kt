package org.rliz.kdgen.common

infix fun <T : Any> Int.times(generator: () -> T): List<T> = (1..this).map { generator() }.toList()
