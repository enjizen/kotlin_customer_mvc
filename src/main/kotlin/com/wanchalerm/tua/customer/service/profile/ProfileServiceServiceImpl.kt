package com.wanchalerm.tua.customer.service.profile

import com.wanchalerm.tua.common.exception.DuplicateException
import com.wanchalerm.tua.common.exception.NoContentException
import com.wanchalerm.tua.customer.model.entity.ProfileEntity
import com.wanchalerm.tua.customer.model.entity.ProfilesEmailEntity
import com.wanchalerm.tua.customer.model.entity.ProfilesMobileEntity
import com.wanchalerm.tua.customer.model.entity.ProfilesPasswordEntity
import com.wanchalerm.tua.customer.model.request.ProfileCreateRequest
import com.wanchalerm.tua.customer.model.request.ProfileMobileUpdateRequest
import com.wanchalerm.tua.customer.model.request.ProfileUpdateRequest
import com.wanchalerm.tua.customer.repository.ProfileEmailRepository
import com.wanchalerm.tua.customer.repository.ProfileMobileRepository
import com.wanchalerm.tua.customer.repository.ProfileRepository
import com.wanchalerm.tua.customer.util.EncodePassword
import jakarta.transaction.Transactional
import java.security.SecureRandom
import java.util.*
import org.springframework.beans.BeanUtils
import org.springframework.stereotype.Service


@Service
class ProfileServiceServiceImpl(private val profileRepository: ProfileRepository,
                                private val profileEmailRepository: ProfileEmailRepository,
                                private val profileMobileRepository: ProfileMobileRepository) : ProfileService {

    @Transactional
    override fun create(profileCreateRequest: ProfileCreateRequest): ProfileEntity {

        checkDuplicateEmailAndMobileNumber(profileCreateRequest)

        val saltNumber = SecureRandom().nextInt(Integer.MAX_VALUE)
        val password = EncodePassword.encode(profileCreateRequest.password!!, saltNumber)
        ProfileEntity().apply {
            BeanUtils.copyProperties(profileCreateRequest, this)
            code = UUID.randomUUID().toString()
            profilesMobiles = mutableSetOf(ProfilesMobileEntity(mobileNumber = profileCreateRequest.mobileNumber, profile = this))
            profilesPasswords = mutableSetOf(ProfilesPasswordEntity(password = password, saltNumber = saltNumber, profile = this))
            profilesEmail = mutableSetOf(ProfilesEmailEntity(email = profileCreateRequest.email, profile = this))
        }.run {
            return profileRepository.save(this)
        }
    }

    override fun update(profileUpdateRequest: ProfileUpdateRequest, id: Int, code: String): ProfileEntity {
        val profileEntity = profileRepository.findByIdAndCodeAndIsDeletedFalse(id, code) ?: throw NoContentException(message = "Not found profile with id: $id and code: $code")
        BeanUtils.copyProperties(profileUpdateRequest, profileEntity)
        return profileRepository.save(profileEntity)
    }

    override fun delete(id: Int, code: String) {
        val profileEntity = profileRepository.findByIdAndCodeAndIsDeletedFalse(id, code) ?: throw NoContentException(message = "Not found profile with id: $id and code: $code")
        profileEntity.isDeleted = true
        profileRepository.save(profileEntity)
    }

    override fun updateMobileNumber(mobileNumber: String, id: Int, code: String): ProfileEntity {
        val profileEntity = profileRepository.findByIdAndCodeAndIsDeletedFalse(id, code) ?: throw NoContentException(message = "Not found profile with id: $id and code: $code")
        val existsMobileNumber = profileMobileRepository.existsByMobileNumber(mobileNumber)
        if (existsMobileNumber) {
            throw DuplicateException(message = "Mobile number $mobileNumber is duplicate")
        }
        profileEntity.profilesMobiles.filter { it.isDeleted == false }.forEach { it.isDeleted = true }
        profileEntity.profilesMobiles.add(ProfilesMobileEntity(mobileNumber = mobileNumber, profile = profileEntity))
        return profileRepository.save(profileEntity)
    }

    internal fun checkDuplicateEmailAndMobileNumber(profileCreateRequest: ProfileCreateRequest) {
        val existsEmail = profileEmailRepository.existsByEmail(profileCreateRequest.email!!)
        if (existsEmail) {
            throw DuplicateException(message = "Email ${profileCreateRequest.email} is duplicate")
        }
        val existsMobileNumber = profileMobileRepository.existsByMobileNumber(profileCreateRequest.mobileNumber!!)
        if (existsMobileNumber) {
            throw DuplicateException(message = "Mobile number ${profileCreateRequest.mobileNumber} is duplicate")
        }
    }
}