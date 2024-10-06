package com.example.orderms

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class OrderMsApplication

fun main(args: Array<String>) {
    runApplication<OrderMsApplication>(*args)
}
