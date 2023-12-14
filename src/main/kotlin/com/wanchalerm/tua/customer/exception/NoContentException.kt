package com.wanchalerm.tua.customer.exception

import com.wanchalerm.tua.customer.constant.ResponseEnum

class NoContentException (
    val code: String = ResponseEnum.NO_CONTENT.code,
    override var message: String = ResponseEnum.NO_CONTENT.message,
    throwable: Throwable? = null
) : RuntimeException(message, throwable)