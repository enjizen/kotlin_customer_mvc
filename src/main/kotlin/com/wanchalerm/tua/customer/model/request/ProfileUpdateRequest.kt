package com.wanchalerm.tua.customer.model.request

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.wanchalerm.tua.common.constant.DateFormatConstant
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDate

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
class ProfileUpdateRequest (
    @field:NotBlank
    val firstName: String? = null,

    @field:NotBlank
    val lastName: String? = null,

    @field:NotNull
    @field:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateFormatConstant.FORMAT_YYYY_MM_DD, timezone = DateFormatConstant.ZONE_ID_BANGKOK)
    val birthDate: LocalDate? = null
)