package com.wanchalerm.tua.customer.service.profileemail

import com.wanchalerm.tua.customer.model.entity.ProfilesEmailEntity

interface ProfileEmailService {
    fun getEmail(email: String): ProfilesEmailEntity
}