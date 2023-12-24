package com.wanchalerm.tua.customer.controller

import com.wanchalerm.tua.common.constant.ResponseEnum
import com.wanchalerm.tua.common.model.response.ResponseModel
import com.wanchalerm.tua.customer.model.entity.ProfileEntity
import com.wanchalerm.tua.customer.model.request.ProfileCreateRequest
import com.wanchalerm.tua.customer.model.request.ProfileMobileUpdateRequest
import com.wanchalerm.tua.customer.model.request.ProfileUpdateRequest
import com.wanchalerm.tua.customer.model.response.ProfileResponse
import com.wanchalerm.tua.customer.service.profile.ProfileService
import jakarta.validation.Valid
import org.springframework.beans.BeanUtils
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/v1")
class ProfileController(private val profileService: ProfileService) {

    @PostMapping("/profiles")
    fun create(@Valid @RequestBody request: ProfileCreateRequest): ResponseEntity<Any> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                ResponseModel(ResponseEnum.SUCCESS, dataObj = toProfileResponse(profileService.create(request)))
            )
    }

    @PutMapping("/profiles")
    fun update(
        @Valid @RequestBody request: ProfileUpdateRequest, @RequestParam("id") id: Int,
        @RequestParam("code") code: String
    ): ResponseEntity<ResponseModel> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ResponseModel(ResponseEnum.SUCCESS, dataObj = toProfileResponse(profileService.update(request, id, code))))
    }

    @DeleteMapping("/profiles")
    fun delete(
        @RequestParam("id") id: Int,
        @RequestParam("code") code: String
    ): ResponseEntity<ResponseModel> {
        profileService.delete(id, code)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ResponseModel(ResponseEnum.SUCCESS))
    }

    @PatchMapping("/profiles/mobile-number")
    fun updateMobileNumber(@Valid @RequestBody request: ProfileMobileUpdateRequest, @RequestParam("id") id: Int, @RequestParam("code") code: String) : ResponseEntity<ResponseModel> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ResponseModel(ResponseEnum.SUCCESS, dataObj = toProfileResponse(profileService.updateMobileNumber(request.mobileNumber!!, id, code))))
    }

    internal fun toProfileResponse(profileEntity: ProfileEntity) : ProfileResponse {
        val profileResponse = ProfileResponse()
        BeanUtils.copyProperties(profileEntity, profileResponse)
        profileResponse.mobileNumber = profileEntity.profilesMobiles.first { it.isDeleted == false }.mobileNumber
        profileResponse.email = profileEntity.profilesEmail.first { it.isDeleted == false }.email
        return profileResponse
    }
}