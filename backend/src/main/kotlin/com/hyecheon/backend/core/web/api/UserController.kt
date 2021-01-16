package com.hyecheon.backend.core.web.api

import com.hyecheon.backend.core.service.*
import com.hyecheon.backend.core.web.dto.*
import org.springframework.http.*
import org.springframework.security.core.context.*
import org.springframework.web.bind.annotation.*


/**
 * @author hyecheon
 * @email rainbow880616@gmail.com
 */
@RequestMapping(value = ["/api/user"])
@RestController
class UserController(
	val userService: UserService,
) {

	@PostMapping(value = ["/signUp"])
	fun signUp(@RequestBody signupUserRequestDto: SignupUserRequestDto) = run {
		ResponseEntity.ok(userService.signUp(signupUserRequestDto))
	}

	@GetMapping
	fun findLoggedUser() = run {
		val authentication = SecurityContextHolder.getContext().authentication
		if (authentication != null) {
			ResponseEntity.ok(LoggedUserDto.from(userService.findUserInfo(authentication.principal.toString())))
		} else {
			ResponseEntity.badRequest()
		}
	}

	@GetMapping(value = ["/{id}"])
	fun findUser(@PathVariable id: Long) = run {
		ResponseEntity.ok(LoggedUserDto.from(userService.findUserInfo(id)))
	}

	@GetMapping(value = ["/findAll"])
	fun findAll(): ResponseEntity<*>? {
		return ResponseEntity.ok("success")
	}

}