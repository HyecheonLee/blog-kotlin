package com.hyecheon.backend.core.exception

import org.hibernate.*
import org.springframework.dao.*
import org.springframework.http.*
import org.springframework.web.bind.annotation.*

/**
 * @author hyecheon
 * @email rainbow880616@gmail.com
 */
@RestControllerAdvice
class ApiExceptionHandler {
	@ExceptionHandler(DataAccessException::class)
	fun dataAccessExceptionHandler(e: DataAccessException) = run {
		val cause = e.cause
		if (cause is JDBCException) {
			val error = cause.sqlException.message
			ResponseEntity(mapOf("message" to error), HttpStatus.BAD_REQUEST)
		} else {
			ResponseEntity(mapOf("message" to cause?.message), HttpStatus.BAD_REQUEST)
		}
	}

	@ExceptionHandler(EmailExistsException::class)
	fun emailExistsExceptionHandler(e: EmailExistsException) = run {
		ResponseEntity(mapOf("message" to e.message), HttpStatus.BAD_REQUEST)
	}

	@ExceptionHandler(UsernameExistsException::class)
	fun usernameExistsExceptionHandler(e: UsernameExistsException) = run {
		ResponseEntity(mapOf("message" to e.message), HttpStatus.BAD_REQUEST)
	}

	@ExceptionHandler(Exception::class)
	fun runtimeExceptionHandler(e: Exception) = run {
		ResponseEntity(mapOf("message" to e.message), HttpStatus.BAD_REQUEST)
	}
}