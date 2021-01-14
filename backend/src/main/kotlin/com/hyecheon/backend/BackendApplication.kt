package com.hyecheon.backend

import org.springframework.boot.*
import org.springframework.boot.autoconfigure.*
import org.springframework.data.jpa.repository.config.*

@EnableJpaAuditing
@SpringBootApplication
class BackendApplication

fun main(args: Array<String>) {
	runApplication<BackendApplication>(*args)
}