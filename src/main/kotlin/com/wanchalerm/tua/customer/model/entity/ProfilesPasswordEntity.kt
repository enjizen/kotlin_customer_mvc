package com.wanchalerm.tua.customer.model.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

@Entity
@Table(name = "profiles_password")
open class ProfilesPasswordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Int? = null

    @Column(name = "password", nullable = false, length = 64)
    open var password: String? = null

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "profile_id", nullable = false)
    open var profile: ProfileEntity? = null

    @Column(name = "created_timstamp", nullable = false)
    open var createdTimstamp: LocalDateTime? = null


    @Column(name = "updated_timestamp", nullable = false)
    open var updatedTimestamp: LocalDateTime? = null


    @Column(name = "is_deleted", nullable = false)
    open var isDeleted: Boolean? = false
}