package com.wanchalerm.tua.customer.model.entity

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import java.time.LocalDate
import java.time.LocalDateTime
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.UpdateTimestamp


@Entity(name = "profiles")
@DynamicUpdate
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
open class ProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @Column(name = "code", nullable = false)
    lateinit var code: String

    @Column(name = "first_name", nullable = false)
    lateinit var firstName: String

    @Column(name = "last_name", nullable = false)
    lateinit var lastName: String

    @Column(name = "birth_date", nullable = false)
    lateinit var birthDate: LocalDate

    @Column(name = "mobile_number", length = 10, nullable = false)
    lateinit var mobileNumber: String

    @Column(name = "email", length = 60, nullable = false)
    lateinit var email: String

    @Column(name = "created_date_time", nullable = false)
    @CreationTimestamp
    lateinit var createdDateTime: LocalDateTime

    @Column(name = "updated_date_time", nullable = false)
    @UpdateTimestamp
    lateinit var updatedDateTime: LocalDateTime

    @Column(name = "is_active", nullable = false)
    var active: Boolean = true

    @Column(name = "is_deleted", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    var deleted: Boolean = false

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "profile", cascade = [CascadeType.ALL])
    private val profilesPasswordEntityList: List<ProfilesPasswordEntity>? = null
}
