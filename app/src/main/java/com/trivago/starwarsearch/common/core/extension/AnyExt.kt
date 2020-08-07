package com.trivago.starwarsearch.common.core.extension

fun <T> Array<T>.getValueAt(index: Int): Any? {
    if (index < 0 || index > (this.size - 1))
        return null

    return try {
        (this[index] as Array<Any?>)[index]
    } catch (ex: ClassCastException) {
        this[index]
    }

}