package com.wanchalerm.tua.customer.repository

import com.wanchalerm.tua.customer.model.entity.ProfilesPasswordEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ProfilePasswordRepository : CrudRepository<ProfilesPasswordEntity, Int> {
    fun existsByPasswordAndId(password: String, id: Int): Boolean
}