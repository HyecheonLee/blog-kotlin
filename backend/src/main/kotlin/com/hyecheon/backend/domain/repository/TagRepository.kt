package com.hyecheon.backend.domain.repository

import com.hyecheon.backend.domain.entity.*
import org.springframework.data.jpa.repository.*
import org.springframework.transaction.annotation.*
import java.util.*

/**
 * @author hyecheon
 * @email rainbow880616@gmail.com
 */

interface TagRepository : JpaRepository<Tag, Long> {
	fun findBySlug(toLowerCase: String): Optional<Tag>

	@Transactional
	fun deleteBySlug(slug: String)
}