package com.hyecheon.backend.web.api

import com.hyecheon.backend.domain.entity.*
import com.hyecheon.backend.domain.repository.*
import javassist.*
import org.springframework.data.domain.*
import org.springframework.data.web.*
import org.springframework.http.*
import org.springframework.web.bind.annotation.*
import javax.validation.*

/**
 * @author hyecheon
 * @email rainbow880616@gmail.com
 */
@RestController
@RequestMapping("/api/v1/tag")
class TagController(private val tagRepository: TagRepository) {
	@PostMapping
	fun create(@Valid @RequestBody tag: Tag) = run {
		ResponseEntity.ok(tagRepository.save(tag))
	}

	@GetMapping
	fun findAll(@PageableDefault(page = 0, size = 10, sort = ["id"], direction = Sort.Direction.DESC) pageable: Pageable) = run {
		val findAll = tagRepository.findAll(pageable)
		ResponseEntity.ok(findAll.content)
	}

	@GetMapping("/{slug}")
	fun findAll(@PathVariable slug: String) = run {
		ResponseEntity.ok(tagRepository.findBySlug(slug.toLowerCase()).orElseThrow { throw NotFoundException("slug[$slug]가 존재 하지 않습니다.") })
	}

	@DeleteMapping("/{slug}")
	fun delete(@PathVariable slug: String) = run {
		tagRepository.deleteBySlug(slug.toLowerCase())
		ResponseEntity.noContent()
	}
}