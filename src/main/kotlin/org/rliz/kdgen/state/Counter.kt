package org.rliz.kdgen.state

import org.rliz.kdgen.data.primitive.CountingInt

class Counter(private var counter: Int = -1, private val next: (Int) -> Int = { x -> x + 1 }) {

    companion object {
        val singletion = Counter()
    }

    operator fun invoke(): Int {
        counter = next(counter)
        return counter
    }

    fun fix() = CountingInt(this)
}