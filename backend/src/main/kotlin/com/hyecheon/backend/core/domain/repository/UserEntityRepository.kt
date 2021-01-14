package com.hyecheon.backend.core.domain.repository

import com.hyecheon.backend.core.domain.entity.*
import org.springframework.data.jpa.repository.*
import java.util.*

/**
 * @author hyecheon
 * @email rainbow880616@gmail.com
 */
interface UserEntityRepository : JpaRepository<UserEntity, Long> {

	fun findByEmail(email: String): Optional<UserEntity>
	fun existsByEmail(email: String): Boolean
	fun existsByUsername(email: String): Boolean
}