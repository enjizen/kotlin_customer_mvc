package com.wanchalerm.tua.customer.service.profilemobile

import com.wanchalerm.tua.customer.model.entity.ProfilesMobileEntity

interface ProfileMobileService {
    fun getMobileNumber(mobileNumber: String): ProfilesMobileEntity
}