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
import java.time.LocalDateTime
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp

@Entity
@Table(name = "profiles_mobile")
class ProfilesMobileEntity(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @field:Column(name = "id", nullable = false)
    var id: Int? = null,

    @field:Column(name = "mobile_number", nullable = false, length = 10, unique = true)
    var mobileNumber: String? = null,

    @field:ManyToOne(fetch = FetchType.LAZY, optional = false)
    @field:JoinColumn(name = "profile_id", nullable = false)
    var profile: ProfileEntity? = null,

    @field:Column(name = "created_timestamp", nullable = false)
    @field:CreationTimestamp
    var createdTimestamp: LocalDateTime? = null,

    @field:Column(name = "updated_timestamp", nullable = false)
    @field:UpdateTimestamp
    var updatedTimestamp: LocalDateTime? = null,

    @field:Column(name = "is_deleted", nullable = false)
    var isDeleted: Boolean? = false,
)