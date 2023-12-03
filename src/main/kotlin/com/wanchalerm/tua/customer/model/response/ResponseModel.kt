package com.wanchalerm.tua.customer.model.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.wanchalerm.tua.customer.constant.ResponseEnum

@JsonPropertyOrder(
    "status",
    "data"
)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
class ResponseModel(
    responseEnum: ResponseEnum? = null,
    responseStatus: ResponseStatus? = null,
    dataObj: Any? = null
) : ResponseCommon() {

    @JsonProperty("data")
    var dataObj: Any? = null

    init {
        if (responseStatus != null) {
            this.status = responseStatus
        } else {
            this.status = ResponseStatus(
                responseEnum?.code,
                responseEnum?.message,
                responseEnum?.description
            )
        }
        this.dataObj = dataObj
    }
}