package com.wanchalerm.tua.customer.model.request

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.wanchalerm.tua.common.constant.DateFormatConstant.FORMAT_YYYY_MM_DD
import com.wanchalerm.tua.common.constant.DateFormatConstant.ZONE_ID_BANGKOK
import com.wanchalerm.tua.common.constant.RegexpConstant.NUMBER_FORMAT_ONLY
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import java.time.LocalDate

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
class CustomerRequest (
    @field:NotBlank
    val firstName: String,

    @field:NotBlank
    val lastName: String,

    @field:NotNull
    @field:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FORMAT_YYYY_MM_DD, timezone = ZONE_ID_BANGKOK)
    val birthDate: LocalDate,

    @field:NotBlank
    @field:Pattern(regexp = NUMBER_FORMAT_ONLY)
    @field:Size(min = 10, max = 10)
    val mobileNumber: String,

    @field:Email
    val email: String
)