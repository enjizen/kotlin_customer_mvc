package com.wanchalerm.tua.customer.service.oauth

interface OauthService {
    fun authenticateWithEmail(email: String, password: String): String?

    fun authenticateWithMobileNumber(mobileNumber: String, password: String): String?
}