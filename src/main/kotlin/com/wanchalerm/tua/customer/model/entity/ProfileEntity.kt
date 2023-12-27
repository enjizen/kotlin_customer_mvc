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
open class ProfileEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Int? = null,

    @Column(name = "code", nullable = false, length = 36)
    var code: String? = null,

    @Column(name = "first_name", nullable = false, length = 50)
    var firstName: String? = null,

    @Column(name = "last_name", nullable = false, length = 50)
    var lastName: String? = null,

    @Column(name = "birth_date")
    var birthDate: LocalDate? = null,

    @Column(name = "created_timestamp", nullable = false)
    @CreationTimestamp
    var createdTimestamp: LocalDateTime? = null,

    @Column(name = "updated_timestamp", nullable = false)
    @UpdateTimestamp
    var updatedTimestamp: LocalDateTime? = null,

    @Column(name = "is_deleted", nullable = false)
    var isDeleted: Boolean? = false,

    @OneToMany(mappedBy = "profile", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var profilesMobiles: MutableSet<ProfilesMobileEntity> = mutableSetOf(),

    @OneToMany(mappedBy = "profile", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var profilesPasswords: MutableSet<ProfilesPasswordEntity> = mutableSetOf(),

    @OneToMany(mappedBy = "profile", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var profilesEmail: MutableSet<ProfilesEmailEntity> = mutableSetOf()
)