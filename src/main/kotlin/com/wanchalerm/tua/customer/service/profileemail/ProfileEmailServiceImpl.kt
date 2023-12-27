package com.wanchalerm.tua.customer.service.profileemail

import com.wanchalerm.tua.common.exception.NoContentException
import com.wanchalerm.tua.customer.model.entity.ProfilesEmailEntity
import com.wanchalerm.tua.customer.repository.ProfileEmailRepository
import org.springframework.stereotype.Service

@Service
class ProfileEmailServiceImpl(private val profileEmailRepository: ProfileEmailRepository) : ProfileEmailService {
    override fun getEmail(email: String): ProfilesEmailEntity {
       return profileEmailRepository.findFirstByEmailAndIsDeletedFalse(email) ?: throw NoContentException(message = "Not found email : $email")
    }
}