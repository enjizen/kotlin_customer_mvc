package com.wanchalerm.tua.customer.service.profile

import com.wanchalerm.tua.customer.model.entity.ProfileEntity
import com.wanchalerm.tua.customer.model.request.ProfileCreateRequest
import com.wanchalerm.tua.customer.model.request.ProfileMobileUpdateRequest
import com.wanchalerm.tua.customer.model.request.ProfileUpdateRequest

interface ProfileService {
    fun create(profileCreateRequest: ProfileCreateRequest): ProfileEntity
    fun update(profileUpdateRequest: ProfileUpdateRequest, id: Int, code: String): ProfileEntity
    fun delete(id: Int, code: String)
    fun updateMobileNumber(mobileNumber: String, id: Int, code: String): ProfileEntity
    fun updatePassword(password: String, id: Int, code: String): ProfileEntity
    fun getByCode(code: String): ProfileEntity
}