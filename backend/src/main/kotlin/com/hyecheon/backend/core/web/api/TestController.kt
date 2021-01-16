package com.hyecheon.backend.core.web.api

import org.springframework.web.bind.annotation.*

/**
 * @author hyecheon
 * @email rainbow880616@gmail.com
 */
@RestController("/api/v1")
class TestController {

	@GetMapping("/test")
	fun test() = run {
		"success";
	}
}