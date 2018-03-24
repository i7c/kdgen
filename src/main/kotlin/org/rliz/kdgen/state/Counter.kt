package org.rliz.kdgen.state

import java.util.concurrent.atomic.AtomicInteger

class Counter(private val counter: AtomicInteger = AtomicInteger(0)) {

    fun next(): Int = counter.getAndIncrement()
}