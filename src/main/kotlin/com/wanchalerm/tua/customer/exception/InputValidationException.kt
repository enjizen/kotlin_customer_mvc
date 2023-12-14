package com.wanchalerm.tua.customer.exception

import com.wanchalerm.tua.customer.constant.ResponseEnum

class InputValidationException(
    val code: String = ResponseEnum.BAD_REQUEST.code,
    override val message: String = ResponseEnum.BAD_REQUEST.message,
    throwable: Throwable? = null
) : RuntimeException(message, throwable)
