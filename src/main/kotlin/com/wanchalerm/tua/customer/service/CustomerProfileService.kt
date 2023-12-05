package com.wanchalerm.tua.customer.service

import com.wanchalerm.tua.customer.exception.NoContentException
import com.wanchalerm.tua.customer.model.entity.ProfileEntity
import com.wanchalerm.tua.customer.model.request.CustomerRequest
import com.wanchalerm.tua.customer.repository.CustomerProfileRepository
import java.util.UUID
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.BeanUtils
import org.springframework.stereotype.Service


@Service
class CustomerProfileService(private val customerProfileRepository: CustomerProfileRepository) {
    val logger: Logger = LoggerFactory.getLogger(this::class.java)

    fun create(customerRequest: CustomerRequest): ProfileEntity {
        ProfileEntity().apply {
            BeanUtils.copyProperties(customerRequest, this)
            code = UUID.randomUUID().toString()
            active = true
            deleted = false
        }.run {
            return customerProfileRepository.save(this)
        }
    }

    fun update(customerRequest: CustomerRequest, id: Int, code: String): ProfileEntity {
        val profileEntity = customerProfileRepository.findByIdAndCodeAndDeletedFalse(id, code) ?: throw NoContentException()
        profileEntity.apply {
            BeanUtils.copyProperties(customerRequest, this)
        }.run {
            return customerProfileRepository.save(this)
        }
    }

    fun delete(id: Int) {
        val profileEntity = customerProfileRepository.findById(id)
        if (!profileEntity.isPresent) {
            throw NoContentException()
        }

        profileEntity.get().apply {
            deleted = true
        }.run {
            customerProfileRepository.save(this)
        }
    }

    fun getAll(): MutableList<ProfileEntity> {
        return customerProfileRepository.findAllByDeletedFalse()
    }
}