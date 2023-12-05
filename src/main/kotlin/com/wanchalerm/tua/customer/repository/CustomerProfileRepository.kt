package com.wanchalerm.tua.customer.repository

import com.wanchalerm.tua.customer.model.entity.ProfileEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository


@Repository
interface CustomerProfileRepository : CrudRepository<ProfileEntity, Int> {
    fun findByIdAndCodeAndDeletedFalse(id: Int, code: String): ProfileEntity
    fun findAllByDeletedFalse(): MutableList<ProfileEntity>
}