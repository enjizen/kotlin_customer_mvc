package com.wanchalerm.tua.customer.interceptor

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import com.wanchalerm.tua.customer.config.MaskingConfig
import com.wanchalerm.tua.customer.constant.ThreadConstant
import com.wanchalerm.tua.customer.extension.createCorrelationId
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import java.io.IOException
import java.util.concurrent.TimeUnit
import org.apache.commons.lang.StringUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.stereotype.Component
import org.springframework.util.StopWatch
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingResponseWrapper


@Component
class LoggingFilter(private val maskingConfig: MaskingConfig) : OncePerRequestFilter() {
    private val log: Logger = LoggerFactory.getLogger(this::class.java)
    private val objectMapper = ObjectMapper()
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

       var correlationId = request.getHeader(ThreadConstant.CORRELATION_ID)
       if (StringUtils.isBlank(correlationId)) {
           correlationId = "customer".createCorrelationId()
       }

       MDC.put(ThreadConstant.CORRELATION_ID, correlationId)
       MDC.put(ThreadConstant.CLIENT_IP, request.remoteAddr)

        val requestWrapper = RepeatableContentCachingRequestWrapper(request)
        val responseWrapper = ContentCachingResponseWrapper(response)
        logRequest(requestWrapper)
        val stopWatch = StopWatch()
        stopWatch.start()
        filterChain.doFilter(requestWrapper, responseWrapper)
        stopWatch.stop()
        logResponse(requestWrapper.method, requestWrapper.requestURI, responseWrapper, stopWatch)
    }

    @Throws(IOException::class)
    private fun logRequest(requestWrapper: RepeatableContentCachingRequestWrapper) {
        val allHeaders = getAllHeaders(requestWrapper)
        val body = if (maskingConfig.enabled) {
            maskJsonValue(requestWrapper.readInputAndDuplicate())
        } else {
            requestWrapper.readInputAndDuplicate()
        }
        val logRequest = """[REQUEST]
                method=[${requestWrapper.method}]
                path=[${requestWrapper.requestURI}]
                headers=[${objectMapper.writeValueAsString(allHeaders)}]
                body=[$body]
            """.trimIndent()
        log.info(logRequest.split("\\s+".toRegex()).joinToString(" "))
    }

    @Throws(IOException::class)
    private fun logResponse(method: String, uri: String, responseWrapper: ContentCachingResponseWrapper, stopWatch: StopWatch) {
        val body = if (maskingConfig.enabled) {
            maskJsonValue(String(responseWrapper.contentAsByteArray))
        } else {
            String(responseWrapper.contentAsByteArray)
        }
        val logResponse = """[RESPONSE]
                timeUsage=[${stopWatch.getTotalTime(TimeUnit.SECONDS)}]
                method=[$method]
                path=[$uri]
                status=[${responseWrapper.status}]
                body=[$body]
            """.trimIndent()
        log.info(logResponse.split("\\s+".toRegex()).joinToString(" "))
        responseWrapper.copyBodyToResponse()
    }

    private fun getAllHeaders(request: RepeatableContentCachingRequestWrapper): Map<String, List<String>> {
        val headersMap = mutableMapOf<String, List<String>>()

        // Get all header names
        val headerNames = request.headerNames

        while (headerNames.hasMoreElements()) {
            val headerName = headerNames.nextElement()
            val headerValues = request.getHeaders(headerName).toList()

            headersMap[headerName] = headerValues
        }

        return headersMap
    }


    private fun replaceFirst(input: String): String {
        val firstPosition = maskingConfig.maskingSize ?: 4
        return if (input.length >= firstPosition) {
            "***${input.substring(firstPosition)}"
        } else {
            "***${input.substring(input.length)}"
        }
    }


    fun maskJsonValue(jsonString: String): String {
        return try {
            val jsonNode: JsonNode = objectMapper.readTree(jsonString)
            maskingConfig.maskingKeys?.forEach { key -> traverseAndMask(jsonNode, key) }
            objectMapper.writeValueAsString(jsonNode)
        } catch (e: Exception) {
            e.printStackTrace()
            jsonString // Return the original JSON in case of an error
        }
    }

    private fun traverseAndMask(jsonNode: JsonNode, keyToMask: String) {
        if (jsonNode.isObject) {
            jsonNode.fields().forEachRemaining { entry ->
                val key: String = entry.key
                if (key == keyToMask) {
                    // Mask the value if the key matches
                    (jsonNode as ObjectNode).put(key,  replaceFirst(entry.value.textValue()))
                } else {
                    // Recursively traverse the value
                    traverseAndMask(entry.value, keyToMask)
                }
            }
        } else if (jsonNode.isArray) {
            jsonNode.elements().forEachRemaining { element -> traverseAndMask(element, keyToMask) }
        }
    }

}