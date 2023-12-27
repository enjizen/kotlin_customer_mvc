package com.wanchalerm.tua.customer.util

import com.google.common.hash.Hashing
import java.nio.charset.StandardCharsets


object EncodePassword {
    fun encode(password: String, salt: Int): String = Hashing.sha256()
        .hashString("$password${password.length + salt}", StandardCharsets.UTF_8)
        .toString()
}