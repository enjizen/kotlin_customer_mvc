package com.wanchalerm.tua.customer.exception

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.exc.InvalidFormatException
import com.wanchalerm.tua.customer.constant.ExceptionConstant.DEFAULT_MESSAGE
import com.wanchalerm.tua.customer.constant.ExceptionConstant.FIELD_IS_INVALID
import com.wanchalerm.tua.customer.constant.ExceptionConstant.FIELD_IS_MISSING
import com.wanchalerm.tua.customer.constant.ResponseEnum
import com.wanchalerm.tua.customer.constant.ResponseStatusConstant.BAD_REQUEST
import com.wanchalerm.tua.customer.extension.camelToSnake
import com.wanchalerm.tua.customer.model.response.ResponseModel
import com.wanchalerm.tua.customer.model.response.ResponseStatus
import jakarta.validation.ConstraintViolationException
import java.util.*
import org.apache.commons.lang.StringUtils
import org.apache.http.entity.ContentType
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpMediaTypeException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingPathVariableException
import org.springframework.web.bind.MissingRequestHeaderException
import org.springframework.web.bind.MissingRequestValueException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.ServletRequestBindingException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException

@RestControllerAdvice
class GlobalException {
    val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(
        JsonParseException::class,
        HttpMediaTypeException::class,
        HttpMessageNotReadableException::class,
        MissingRequestHeaderException::class,
        MissingRequestValueException::class,
        ServletRequestBindingException::class,
        MethodArgumentTypeMismatchException::class
    )
    @ResponseBody
    fun handleJsonParseException(ex: Exception): ResponseEntity<ResponseModel> {
        logger.error("handleJsonParseException", ex)
        val description = getDescription(ex)
        val responseStatus = ResponseStatus("400", BAD_REQUEST, description)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .header(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString())
            .body(ResponseModel(responseStatus = responseStatus))
    }

    @ExceptionHandler(
        MethodArgumentNotValidException::class,
        ConstraintViolationException::class
    )
    @ResponseBody
    fun handleBadRequest(ex: Exception): ResponseEntity<ResponseModel> {
        logger.error("handleBadRequest", ex)
        val errorMap = TreeMap<String, String>()

        if (ex is MethodArgumentNotValidException) {
            ex.bindingResult.fieldErrors.forEach { error ->
                val errorDescription = if (error.rejectedValue == null) FIELD_IS_MISSING else FIELD_IS_INVALID
                error.field.camelToSnake().let { errorMap[it] = String.format(errorDescription, it) }
            }

            ex.bindingResult.globalErrors.forEach { error ->
                val errorDescription = if (error.defaultMessage == "null") FIELD_IS_MISSING else FIELD_IS_INVALID
                error.objectName.camelToSnake().let {errorMap[it] = String.format(errorDescription, it) }
            }
        }

       (ex as? ConstraintViolationException)?.constraintViolations?.forEach { error ->
            val errorDescription = if (null == error.invalidValue) FIELD_IS_MISSING else FIELD_IS_INVALID
            val paths = StringUtils.split(error.propertyPath.toString(), ".")
            val fieldNameSnakeCase: String = paths[paths.size - 1].camelToSnake()
            errorMap[fieldNameSnakeCase] = String.format(errorDescription, fieldNameSnakeCase)
        }

        val responseStatus = ResponseStatus(ResponseEnum.BAD_REQUEST.code, ResponseEnum.BAD_REQUEST.message,  errorMap.firstEntry().value)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .header(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString())
            .body(ResponseModel(responseStatus = responseStatus))

    }

    private fun getDescription(exception: Exception): String {
        return when {
            exception.cause is JsonMappingException && (exception.cause as JsonMappingException).cause is InputValidationException -> {
                val cause = exception.cause as JsonMappingException
                String.format(FIELD_IS_INVALID, referencesToFields(cause.path))
            }

            exception.cause is InvalidFormatException -> {
                val cause = exception.cause as InvalidFormatException
                String.format(FIELD_IS_INVALID, referencesToFields(cause.path))
            }

            exception is MissingServletRequestParameterException -> {
                String.format(FIELD_IS_MISSING, exception.parameterName)
            }

            exception is MissingPathVariableException -> {
                String.format(FIELD_IS_MISSING, exception.variableName)
            }

            exception is MethodArgumentTypeMismatchException -> {
                String.format(FIELD_IS_INVALID, exception.name)
            }

            else -> DEFAULT_MESSAGE
        }

    }

    private fun referencesToFields(references: List<JsonMappingException.Reference>): String {
        val sb = StringBuilder()
        for (ref in references) {
            if (ref.fieldName == null) {
                sb.deleteCharAt(sb.length - 1)
                    .append("[")
                    .append(ref.index)
                    .append("]")
            } else {
                sb.append(ref.fieldName)
            }
            sb.append(".")
        }
        return sb.substring(0, sb.length - 1)
    }

    @ExceptionHandler(BusinessException::class)
    @ResponseBody
    fun handleBusinessException(ex: BusinessException): ResponseEntity<ResponseModel> {
        logger.error(ex.message, ex)
        return ResponseEntity.status(ex.httpStatus)
            .header(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString())
            .body(ResponseModel(responseStatus = ResponseStatus(ex.code, ex.message, ex.description)))
    }

    @ExceptionHandler(InputValidationException::class)
    @ResponseBody
    fun handleInputValidationException(ex: InputValidationException): ResponseEntity<ResponseModel> {
        logger.error(ex.message, ex)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .header(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString())
            .body(ResponseModel(responseStatus = ResponseStatus(ex.code, ex.message, ex.description)))
    }

    @ExceptionHandler(NoContentException::class)
    @ResponseBody
    fun handleNoContentExceptionException(ex: NoContentException): ResponseEntity<ResponseModel> {
        logger.error(ex.message, ex)
        val responseModel = ResponseModel(responseStatus = ResponseStatus(ex.code, ex.message, ex.description))
        return ResponseEntity.status(HttpStatus.OK)
            .header(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString())
            .body(responseModel)
    }

    @ExceptionHandler(UnauthorizedException::class)
    @ResponseBody
    fun handleUnauthorizedException(ex: UnauthorizedException): ResponseEntity<ResponseModel> {
        logger.error(ex.message, ex)
        val responseModel = ResponseModel(responseStatus = ResponseStatus(ex.code, ex.message, ex.description))
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .header(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString())
            .body(responseModel)
    }

    @ExceptionHandler(DuplicateException::class)
    @ResponseBody
    fun handleDuplicateException(ex: DuplicateException): ResponseEntity<ResponseModel> {
        logger.error(ex.message, ex)
        val responseModel = ResponseModel(responseStatus = ResponseStatus(ex.code, ex.message, ex.description))
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .header(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString())
            .body(responseModel)
    }
}