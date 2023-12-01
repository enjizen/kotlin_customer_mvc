package com.wanchalerm.tua.customer.model.entity

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDate
import java.time.LocalDateTime
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.UpdateTimestamp

@Entity(name = "profiles")
@DynamicUpdate
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
  class ProfileEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    var id: Int? = null

    @Column(name = "code")
    var code: String? = null

    @Column(name = "first_name")
    var firstName: String? = null

    @Column(name = "last_name")
    var lastName: String? = null

    @Column(name = "birth_date")
    var birthDate: LocalDate? = null

    @Column(name = "mobile_number")
    var mobileNumber: String? = null

    @Column(name = "email")
    var email: String? = null

    @Column(name = "created_date_time")
    @CreationTimestamp
    var createdDateTime: LocalDateTime? = null

    @Column(name = "updated_date_time")
    @UpdateTimestamp
    var updatedDateTime: LocalDateTime? = null

    @Column(name = "is_active")
    var active: Boolean? = null

    @Column(name = "is_deleted")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    var deleted: Boolean? = null
}
