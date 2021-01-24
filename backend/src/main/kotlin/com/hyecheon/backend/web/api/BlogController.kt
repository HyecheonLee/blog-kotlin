package com.hyecheon.backend.web.api

import com.hyecheon.backend.domain.entity.*
import com.hyecheon.backend.domain.repository.*
import org.springframework.http.*
import org.springframework.validation.*
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.*
import javax.validation.*


/**
 * @author hyecheon
 * @email rainbow880616@gmail.com
 */
@RequestMapping("/api/v1/blog")
@RestController
class BlogController(
	val blogRepository: BlogRepository
) {
	@PostMapping
	fun createBlog(@ModelAttribute @Valid blog: Blog, file: MultipartFile? = null, errors: Errors) = run {
		println(errors)
		blog.photo = file?.let { Files.from(it) }
		blogRepository.save(blog)
		val save = blogRepository.save(blog)
		ResponseEntity.ok(save)
	}
}