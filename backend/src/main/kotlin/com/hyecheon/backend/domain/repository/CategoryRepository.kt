package com.hyecheon.backend.domain.repository

import com.hyecheon.backend.domain.entity.*
import org.springframework.data.jpa.repository.*
import org.springframework.transaction.annotation.*
import java.util.*

/**
 * @author hyecheon
 * @email rainbow880616@gmail.com
 */
interface CategoryRepository : JpaRepository<Category, Long> {
	fun findBySlug(slug: String): Optional<Category>

	@Transactional
	fun deleteBySlug(slug: String)
}