package com.wanchalerm.tua.customer.extension

import java.util.*
import org.apache.commons.lang.StringUtils

fun String?.camelToSnake(): String =
    this?.replace("([a-z])([A-Z]+)".toRegex(), "$1_$2")?.lowercase(Locale.getDefault()) ?: ""

fun String.createCorrelationId(): String {
    val correlationId = "$this-${
        UUID.randomUUID().toString()
            .replace("-", "")
            .lowercase()
    }"
    return StringUtils.left(correlationId, 32)
}
