package com.wanchalerm.tua.customer.service.profilemobile

import com.wanchalerm.tua.common.exception.NoContentException
import com.wanchalerm.tua.customer.model.entity.ProfilesMobileEntity
import com.wanchalerm.tua.customer.repository.ProfileMobileRepository
import org.springframework.stereotype.Service

@Service
class ProfileMobileServiceImpl(private val profileMobileRepository: ProfileMobileRepository) : ProfileMobileService {
    override fun getMobileNumber(mobileNumber: String): ProfilesMobileEntity {
        return profileMobileRepository.findFirstByMobileNumberAndIsDeletedFalse(mobileNumber)
            ?: throw NoContentException(
                message = "Not found mobile number : $mobileNumber"
            )
    }


}