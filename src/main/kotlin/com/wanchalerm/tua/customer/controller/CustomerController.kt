package com.wanchalerm.tua.customer.controller

import com.wanchalerm.tua.customer.constant.ResponseEnum
import com.wanchalerm.tua.customer.model.request.CustomerRequest
import com.wanchalerm.tua.customer.model.response.ResponseModel
import com.wanchalerm.tua.customer.service.CustomerProfileService
import jakarta.validation.Valid
import java.util.concurrent.ExecutionException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/v1")
class CustomerController(private val customerProfileService: CustomerProfileService) {
    val logger: Logger = LoggerFactory.getLogger(this::class.java)
    @GetMapping("/customers")
    @ResponseStatus(HttpStatus.OK)
    @Throws(
        InterruptedException::class,
        ExecutionException::class
    )
    fun getAllCustomerProfiles(): ResponseEntity<Any> {
        val customerProfilesResult = customerProfileService.getAll()
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ResponseModel(ResponseEnum.SUCCESS, dataObj = customerProfilesResult))
    }

    @PostMapping("/customers")
    fun create(@Valid @RequestBody request: CustomerRequest): ResponseEntity<Any> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ResponseModel(ResponseEnum.SUCCESS, dataObj = customerProfileService.create(request)))
    }

    @PutMapping("/customers")
    fun update(@Valid @RequestBody request: CustomerRequest, @RequestParam("id") id: Int,
               @RequestParam("code") code: String): ResponseEntity<ResponseModel> {
        logger.info("Update profile with id $id and code $code")
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ResponseModel(ResponseEnum.SUCCESS, dataObj = customerProfileService.update(request, id, code)))
    }

    @DeleteMapping("/customers/{id}")
    fun delete(@PathVariable("id") id: Int): ResponseEntity<ResponseModel> {
        customerProfileService.delete(id)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ResponseModel(ResponseEnum.SUCCESS))
    }
}