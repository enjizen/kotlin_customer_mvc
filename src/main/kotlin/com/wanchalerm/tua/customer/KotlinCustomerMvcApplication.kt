package com.wanchalerm.tua.customer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication(
    scanBasePackages = [
        "com.wanchalerm.tua.customer.controller",
        "com.wanchalerm.tua.customer.service",
        "com.wanchalerm.tua.customer.repository",
        "com.wanchalerm.tua.customer.aspect",
        "com.wanchalerm.tua.common.config",
        "com.wanchalerm.tua.common.exception",
        "com.wanchalerm.tua.common.filter"]
)
@EnableDiscoveryClient
class KotlinCustomerMvcApplication

fun main(args: Array<String>) {
    runApplication<KotlinCustomerMvcApplication>(*args)
}
