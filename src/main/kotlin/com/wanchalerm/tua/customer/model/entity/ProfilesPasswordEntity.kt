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
import jakarta.validation.constraints.Size
import java.time.LocalDateTime
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp

@Entity
@Table(name = "profiles_password")
open class ProfilesPasswordEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Int? = null,

    @Column(name = "password", nullable = false, length = 64)
    var password: String? = null,
    
    @Column(name = "salt_number", nullable = false)
    var saltNumber: Int? = null,
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "profile_id", nullable = false)
    var profile: ProfileEntity? = null,

    @Column(name = "created_timestamp", nullable = false)
    @CreationTimestamp
    var createdTimestamp: LocalDateTime? = null,

    @Column(name = "updated_timestamp", nullable = false)
    @UpdateTimestamp
    var updatedTimestamp: LocalDateTime? = null,

    @Column(name = "is_deleted", nullable = false)
    var isDeleted: Boolean? = false
)