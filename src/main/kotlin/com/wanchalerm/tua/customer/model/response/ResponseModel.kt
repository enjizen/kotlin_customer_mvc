package com.wanchalerm.tua.customer.model.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.wanchalerm.tua.customer.constant.ResponseEnum
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@JsonPropertyOrder(
    "status",
    "data")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
class ResponseModel(responseEnum: ResponseEnum? = null, responseStatus: ResponseStatus? = null, dataObj: Any? = null) : ResponseCommon() {
/*     @JsonProperty("status")
     var responseStatus: ResponseStatus? = null*/

     @JsonProperty("data")
     var dataObj: Any? = null

    init {
        if (responseStatus != null) {
            this.status = responseStatus
        } else {
            this.status = ResponseStatus(responseEnum?.code, responseEnum?.message, responseEnum?.description)
        }
        this.dataObj = dataObj
       // this.dataObj = dataObj
     /*   when (dataObj) {
            is Mono<*> -> this.dataObj = dataObj.toFuture().get()
            is Flux<*> -> this.dataObj = dataObj.collectList().toFuture().get()
            else -> this.dataObj = dataObj
        }*/
    }
 }