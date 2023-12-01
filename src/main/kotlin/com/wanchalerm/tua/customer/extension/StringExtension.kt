package com.wanchalerm.tua.customer.extension

import java.util.*

fun String.camelToSnake(): String {
    return this
        .replace("([a-z])([A-Z]+)".toRegex(), "$1_$2")
        .lowercase(Locale.getDefault())
}
