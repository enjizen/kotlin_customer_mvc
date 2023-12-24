package com.wanchalerm.tua.customer.repository

import com.wanchalerm.tua.customer.model.entity.ProfilesMobileEntity
import org.springframework.data.repository.CrudRepository

interface ProfileMobileRepository : CrudRepository<ProfilesMobileEntity, Int> {
    fun existsByMobileNumber(mobileNumber: String): Boolean
}