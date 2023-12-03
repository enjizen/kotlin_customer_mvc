package com.wanchalerm.tua.customer.exception

import com.wanchalerm.tua.customer.constant.ResponseEnum

class DuplicateException(
    var code: String? = ResponseEnum.BAD_REQUEST.code,
    override var message: String? = ResponseEnum.BAD_REQUEST.message,
    var description: String? = ResponseEnum.BAD_REQUEST.description,
    throwable: Throwable? = null
) : RuntimeException(message, throwable)
