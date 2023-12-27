package com.wanchalerm.tua.customer.repository

import com.wanchalerm.tua.customer.model.entity.ProfilesEmailEntity
import org.springframework.data.repository.CrudRepository

interface ProfileEmailRepository : CrudRepository<ProfilesEmailEntity, Int> {
    fun existsByEmail(email: String): Boolean
    fun findFirstByEmailAndIsDeletedFalse(email: String): ProfilesEmailEntity?
}