package com.wanchalerm.tua.customer.service.oauth

interface OauthProfileService {
    fun authentication(username: String, password: String): String?
}