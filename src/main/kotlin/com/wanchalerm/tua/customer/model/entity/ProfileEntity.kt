package com.wanchalerm.tua.customer.model.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.time.LocalDate
import java.time.LocalDateTime
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp

@Entity
@Table(name = "profiles")
class ProfileEntity(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @field:Column(name = "id", nullable = false)
    var id: Int? = null,

    @field:Column(name = "code", nullable = false, length = 36)
    var code: String? = null,

    @field:Column(name = "first_name", nullable = false, length = 50)
    var firstName: String? = null,

    @field:Column(name = "last_name", nullable = false, length = 50)
    var lastName: String? = null,

    @field:Column(name = "birth_date")
    var birthDate: LocalDate? = null,

    @field:Column(name = "created_timestamp", nullable = false)
    @field:CreationTimestamp
    var createdTimestamp: LocalDateTime? = null,

    @field:Column(name = "updated_timestamp", nullable = false)
    @field:UpdateTimestamp
    var updatedTimestamp: LocalDateTime? = null,

    @field:Column(name = "is_deleted", nullable = false)
    var isDeleted: Boolean? = false,

    @field:OneToMany(mappedBy = "profile", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var profilesMobiles: MutableSet<ProfilesMobileEntity> = mutableSetOf(),

    @field:OneToMany(mappedBy = "profile", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var profilesPasswords: MutableSet<ProfilesPasswordEntity> = mutableSetOf(),

    @field:OneToMany(mappedBy = "profile", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var profilesEmail: MutableSet<ProfilesEmailEntity> = mutableSetOf()
)