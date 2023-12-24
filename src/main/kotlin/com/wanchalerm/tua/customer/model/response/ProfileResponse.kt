package com.wanchalerm.tua.customer.model.response

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.time.LocalDate
import java.time.LocalDateTime

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
class ProfileResponse (
    var id: Int? = null,
    var code: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var birthDate: LocalDate? = null,
    var mobileNumber: String? = null,
    var email: String? = null,
    var createdTimestamp: LocalDateTime? = null,
    var updatedTimestamp: LocalDateTime? = null
)