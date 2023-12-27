package com.wanchalerm.tua.customer.service.oauth

interface OauthService {
    fun authentication(email: String? = null, mobileNumber: String? = null, password: String): String?
}