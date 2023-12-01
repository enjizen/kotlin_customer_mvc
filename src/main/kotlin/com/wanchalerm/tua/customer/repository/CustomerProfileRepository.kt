package com.wanchalerm.tua.customer.repository

import com.wanchalerm.tua.customer.model.entity.ProfileEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface CustomerProfileRepository : JpaRepository<ProfileEntity, Int> {
    fun findByIdAndCodeAndDeletedFalse(id: Int, code: String): ProfileEntity?
}