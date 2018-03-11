package org.rliz.kdgen.data

interface LazyValue<out T : Any> {

    fun eval(): T

}