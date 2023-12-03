package com.wanchalerm.tua.customer.exception

import com.wanchalerm.tua.customer.constant.ResponseEnum

class NoContentException (
    var code: String? = ResponseEnum.NO_CONTENT.code,
    override var message: String? = ResponseEnum.NO_CONTENT.message,
    var description: String? = ResponseEnum.NO_CONTENT.description,
    throwable: Throwable? = null
) : RuntimeException(message, throwable)