package com.wanchalerm.tua.customer.service

import com.wanchalerm.tua.common.exception.NoContentException
import com.wanchalerm.tua.customer.model.entity.ProfileEntity
import com.wanchalerm.tua.customer.model.request.CustomerRequest
import com.wanchalerm.tua.customer.repository.ProfileRepository
import java.util.UUID
import org.springframework.beans.BeanUtils
import org.springframework.stereotype.Service


@Service
class ProfileServiceServiceImpl(private val profileRepository: ProfileRepository) : ProfileService {

    override fun create(customerRequest: CustomerRequest): ProfileEntity {
        ProfileEntity().apply {
            BeanUtils.copyProperties(customerRequest, this)
            code = UUID.randomUUID().toString()
            active = true
            deleted = false
        }.run {
            return profileRepository.save(this)
        }
    }

    override fun update(customerRequest: CustomerRequest, id: Int, code: String): ProfileEntity {
        val profileEntity = profileRepository.findByIdAndCodeAndDeletedFalse(id, code) ?: throw NoContentException(message = "Not found profile with id: $id and code: $code")
        BeanUtils.copyProperties(customerRequest, profileEntity)
        return profileRepository.save(profileEntity)
    }

    override fun delete(id: Int, code: String) {
        val profileEntity = profileRepository.findByIdAndCodeAndDeletedFalse(id, code) ?: throw NoContentException(message = "Not found profile with id: $id and code: $code")
        profileEntity.deleted = true
        profileRepository.save(profileEntity)
    }

    override fun getAll(): MutableList<ProfileEntity> = profileRepository.findAllByDeletedFalse()
        ?: throw NoContentException()
}