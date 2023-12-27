package com.wanchalerm.tua.customer.controller

import com.wanchalerm.tua.common.constant.ResponseEnum
import com.wanchalerm.tua.common.model.response.ResponseModel
import com.wanchalerm.tua.customer.model.request.AuthenticationRequest
import com.wanchalerm.tua.customer.model.response.AuthenticationResponse
import com.wanchalerm.tua.customer.service.oauth.OauthService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1")
class ProfileEmailController(private val oauthService: OauthService) {

    @PostMapping("/verify/email-pass")
    fun authenticationWithEmail(@Valid @RequestBody authenticationRequest: AuthenticationRequest) : ResponseEntity<ResponseModel> {
       val code = oauthService.authentication(email = authenticationRequest.username, password = authenticationRequest.password!!)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ResponseModel(ResponseEnum.SUCCESS, dataObj = AuthenticationResponse(customerCode = code)))
    }

}