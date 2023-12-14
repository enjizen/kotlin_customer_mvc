package com.wanchalerm.tua.customer.exception

import com.wanchalerm.tua.customer.constant.ResponseEnum

class UnauthorizedException (
    var code: String? = ResponseEnum.UNAUTHORIZED.code,
    override var message: String? = ResponseEnum.UNAUTHORIZED.message,
    throwable: Throwable? = null
) : RuntimeException(message, throwable)