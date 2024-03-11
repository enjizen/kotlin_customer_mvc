package com.wanchalerm.tua.customer.service.oauth

import com.wanchalerm.tua.common.constant.ResponseEnum
import com.wanchalerm.tua.common.exception.BusinessException
import com.wanchalerm.tua.common.exception.NoContentException
import com.wanchalerm.tua.customer.model.entity.ProfileEntity
import com.wanchalerm.tua.customer.service.profileemail.ProfileEmailService
import com.wanchalerm.tua.customer.service.profilemobile.ProfileMobileService
import com.wanchalerm.tua.customer.util.EncodePassword
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class OauthServiceImpl(
    private val profileEmailService: ProfileEmailService,
    private val profileMobileService: ProfileMobileService
) : OauthService {

    val logger: Logger = LoggerFactory.getLogger(this::class.java)

    override fun authenticateWithEmail(email: String, password: String): String? {
        val profile = profileEmailService.getEmail(email).profile ?: throw NoContentException("$email not found profile")
        return authenticateProfile(profile, password)
    }

    override fun authenticateWithMobileNumber(mobileNumber: String, password: String): String? {
        val profile = profileMobileService.getMobileNumber(mobileNumber).profile ?: throw NoContentException("$mobileNumber not found profile")
        return authenticateProfile(profile, password)
    }

    private fun authenticateProfile(profile: ProfileEntity, password: String): String? {
        val profilePassword = profile.profilesPasswords.firstOrNull { !it.isDeleted } ?: throw NoContentException("profile ${profile.code} not found password")
        val saltNumber = profilePassword.saltNumber ?: throw NoContentException("profile ${profile.code} saltNumber not found")

        if (EncodePassword.encode(password, saltNumber) != profilePassword.password) {
            throw BusinessException(code = ResponseEnum.CONFLICT.code, "Password does not match")
        } else {
            logger.info("Authentication successful for {}", profile.code)
            return profile.code
        }
    }
}