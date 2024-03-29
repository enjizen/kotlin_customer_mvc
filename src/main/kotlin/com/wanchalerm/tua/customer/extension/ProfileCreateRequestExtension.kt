package com.wanchalerm.tua.customer.extension

import com.wanchalerm.tua.customer.model.entity.ProfileEntity
import com.wanchalerm.tua.customer.model.entity.ProfilesEmailEntity
import com.wanchalerm.tua.customer.model.entity.ProfilesMobileEntity
import com.wanchalerm.tua.customer.model.entity.ProfilesPasswordEntity
import com.wanchalerm.tua.customer.model.request.ProfileCreateRequest
import java.util.*

fun ProfileCreateRequest.buildProfileEntity(
    saltNumber: Int,
    password: String
): ProfileEntity {
    val profileCreateRequest = this
    return ProfileEntity().apply {
        code = UUID.randomUUID().toString()
        firstName = profileCreateRequest.firstName
        lastName = profileCreateRequest.lastName
        birthDate = profileCreateRequest.birthDate
        profilesMobiles = mutableSetOf(
            ProfilesMobileEntity(
                mobileNumber = profileCreateRequest.mobileNumber,
                profile = this
            )
        )
        profilesPasswords = mutableSetOf(
            ProfilesPasswordEntity(
                password = password,
                saltNumber = saltNumber,
                profile = this
            )
        )
        profilesEmail = mutableSetOf(
            ProfilesEmailEntity(
                email = profileCreateRequest.email,
                profile = this
            )
        )
    }
}