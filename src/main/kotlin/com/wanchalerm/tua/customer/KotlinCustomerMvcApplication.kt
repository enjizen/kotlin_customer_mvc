package com.wanchalerm.tua.customer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
class KotlinCustomerMvcApplication

fun main(args: Array<String>) {
	runApplication<KotlinCustomerMvcApplication>(*args)
}
