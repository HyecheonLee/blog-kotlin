package com.hyecheon.backend.web.api

import com.hyecheon.backend.domain.*
import com.hyecheon.backend.repository.*
import org.springframework.http.*
import org.springframework.web.bind.annotation.*
import javax.validation.*

/**
 * @author hyecheon
 * @email rainbow880616@gmail.com
 */
@RequestMapping("/api/v1/category")
@RestController
class CategoryController(private val categoryRepository: CategoryRepository) {
	@PostMapping
	fun create(@Valid @RequestBody category: Category) = run {
		ResponseEntity.ok(categoryRepository.save(category))
	}
}