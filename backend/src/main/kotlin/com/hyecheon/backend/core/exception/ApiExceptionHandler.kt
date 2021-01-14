package com.hyecheon.backend.core.exception

import org.springframework.http.*
import org.springframework.web.bind.annotation.*

/**
 * @author hyecheon
 * @email rainbow880616@gmail.com
 */
@RestControllerAdvice
class ApiExceptionHandler {
	@ExceptionHandler(Exception::class)
	fun runtimeExceptionHandler(e: Exception) = run {
		ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
	}

	@ExceptionHandler(EmailExistsException::class)
	fun emailExistsExceptionHandler(e: EmailExistsException) = run {
		ResponseEntity(mapOf("message" to e.message), HttpStatus.BAD_REQUEST)
	}

	@ExceptionHandler(UsernameExistsException::class)
	fun usernameExistsExceptionHandler(e: UsernameExistsException) = run {
		ResponseEntity(mapOf("message" to e.message), HttpStatus.BAD_REQUEST)
	}
}