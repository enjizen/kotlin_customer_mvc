package com.wanchalerm.tua.customer.service.oauth

import com.wanchalerm.tua.common.constant.ResponseEnum
import com.wanchalerm.tua.common.exception.BusinessException
import com.wanchalerm.tua.common.exception.InputValidationException
import com.wanchalerm.tua.common.exception.NoContentException
import com.wanchalerm.tua.common.extension.isValidEmail
import com.wanchalerm.tua.common.extension.isValidMobileNumber
import com.wanchalerm.tua.customer.service.profileemail.ProfileEmailService
import com.wanchalerm.tua.customer.service.profilemobile.ProfileMobileService
import com.wanchalerm.tua.customer.util.EncodePassword
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class OauthProfileServiceImpl(
    private val profileEmailService: ProfileEmailService,
    private val profileMobileService: ProfileMobileService
) : OauthProfileService {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)
    override fun authentication(username: String, password: String): String? {
        return (if (username.isValidEmail())
            profileEmailService.getEmail(username).profile
        else if (username.isValidMobileNumber())
            profileMobileService.getMobileNumber(username).profile
        else  throw InputValidationException(message = "Username type not found")
                )?.let { profile ->
                val profilePassword = profile.profilesPasswords.firstOrNull { it.isDeleted == false }
                    ?: throw NoContentException("profile ${profile.code} not found password")
                val saltNumber =
                    profilePassword.saltNumber
                        ?: throw NoContentException("profile ${profile.code} saltNumber not found")
                if (EncodePassword.encode(password, saltNumber) != profilePassword.password) {
                    throw BusinessException(code = ResponseEnum.CONFLICT.code, "password not match")
                } else {
                    logger.info("email : $username verify password success")
                    profile.code
                }
            } ?: throw NoContentException("$username not found profile file")
    }
}