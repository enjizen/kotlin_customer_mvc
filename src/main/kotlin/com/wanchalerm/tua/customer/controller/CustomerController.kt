package com.wanchalerm.tua.customer.controller

import com.wanchalerm.tua.customer.constant.ResponseEnum
import com.wanchalerm.tua.customer.model.request.CustomerRequest
import com.wanchalerm.tua.customer.model.response.ResponseModel
import com.wanchalerm.tua.customer.service.ProfileService
import jakarta.validation.Valid
import java.util.concurrent.ExecutionException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/v1")
class CustomerController(private val profileService: ProfileService) {
    @GetMapping("/customers")
    @ResponseStatus(HttpStatus.OK)
    @Throws(
        InterruptedException::class,
        ExecutionException::class
    )
    fun getAllCustomerProfiles(): ResponseEntity<Any> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ResponseModel(ResponseEnum.SUCCESS, dataObj = profileService.getAll()))
    }

    @PostMapping("/customers")
    fun create(@Valid @RequestBody request: CustomerRequest): ResponseEntity<Any> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ResponseModel(ResponseEnum.SUCCESS, dataObj = profileService.create(request)))
    }

    @PutMapping("/customers")
    fun update(@Valid @RequestBody request: CustomerRequest, @RequestParam("id") id: Int,
               @RequestParam("code") code: String): ResponseEntity<ResponseModel> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ResponseModel(ResponseEnum.SUCCESS, dataObj = profileService.update(request, id, code)))
    }

    @DeleteMapping("/customers")
    fun delete(@RequestParam("id") id: Int,
               @RequestParam("code") code: String): ResponseEntity<ResponseModel> {
        profileService.delete(id, code)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ResponseModel(ResponseEnum.SUCCESS))
    }
}