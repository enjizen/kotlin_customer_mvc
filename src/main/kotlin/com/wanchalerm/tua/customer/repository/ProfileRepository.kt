package com.wanchalerm.tua.customer.repository

import com.wanchalerm.tua.customer.model.entity.ProfileEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository


@Repository
interface ProfileRepository : CrudRepository<ProfileEntity, Int> {
    fun findByIdAndCodeAndIsDeletedFalse(id: Int, code: String): ProfileEntity?
}