package com.wanchalerm.tua.customer.exception

import com.wanchalerm.tua.customer.constant.ResponseEnum
import org.springframework.http.HttpStatus

class UnauthorizedException (
    var code: String? = ResponseEnum.UNAUTHORIZED.code,
    override var message: String? = ResponseEnum.UNAUTHORIZED.message,
    var description: String? = ResponseEnum.UNAUTHORIZED.description,
    throwable: Throwable? = null
) : RuntimeException(message, throwable)