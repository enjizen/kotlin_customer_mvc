package com.wanchalerm.tua.customer.service

import com.wanchalerm.tua.customer.model.entity.ProfileEntity
import com.wanchalerm.tua.customer.model.request.CustomerRequest

interface ProfileService {
    fun create(customerRequest: CustomerRequest): ProfileEntity
    fun update(customerRequest: CustomerRequest, id: Int, code: String): ProfileEntity
    fun delete(id: Int, code: String)
    fun getAll(): MutableList<ProfileEntity>
}