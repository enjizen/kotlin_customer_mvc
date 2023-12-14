package com.wanchalerm.tua.customer.extension

import java.util.*
import org.apache.commons.lang.StringUtils

fun String?.camelToSnake(): String =
    this?.replace("([a-z])([A-Z]+)".toRegex(), "$1_$2")?.lowercase(Locale.getDefault()) ?: ""

fun String.createCorrelationId(): String =
    "$this-${UUID.randomUUID().toString().replace("-", "").lowercase()}"
        .let {StringUtils.left(it, 32)}
