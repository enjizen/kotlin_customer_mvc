package com.wanchalerm.tua.customer.model.request

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import jakarta.validation.constraints.NotBlank

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
class ProfileMobileUpdateRequest (
    @field:NotBlank
    val mobileNumber: String? = null
)