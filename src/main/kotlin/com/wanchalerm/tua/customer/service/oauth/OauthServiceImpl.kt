package com.wanchalerm.tua.customer.service.oauth

import com.wanchalerm.tua.common.constant.ResponseEnum
import com.wanchalerm.tua.common.exception.BusinessException
import com.wanchalerm.tua.common.exception.NoContentException
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
    override fun authentication(email: String?, mobileNumber: String?, password: String): String? {
        return (if (email?.isNotBlank() == true)
            profileEmailService.getEmail(email).profile
        else if (mobileNumber?.isNotBlank() == true)
            profileMobileService.getMobileNumber(mobileNumber).profile
        else throw BusinessException(code = ResponseEnum.CONFLICT.code, "Input not email or password")
                )?.let { profile ->
                val profilePassword = profile.profilesPasswords.firstOrNull { it.isDeleted == false }
                    ?: throw NoContentException("profile ${profile.code} not found password")
                val saltNumber =
                    profilePassword.saltNumber
                        ?: throw NoContentException("profile ${profile.code} saltNumber not found")
                if (EncodePassword.encode(password, saltNumber) != profilePassword.password) {
                    throw BusinessException(code = ResponseEnum.CONFLICT.code, "password not match")
                } else {
                    logger.info("email : $email verify password success")
                    profile.code
                }
            } ?: throw NoContentException("$email not found profile file")
    }
}