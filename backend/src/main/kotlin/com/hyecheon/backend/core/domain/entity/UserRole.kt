package com.hyecheon.backend.core.domain.entity

import com.hyecheon.backend.core.domain.*
import javax.persistence.*

/**
 * @author hyecheon
 * @email rainbow880616@gmail.com
 */
@Entity
data class UserRole(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	var id: Long? = null,
	@Enumerated(value = EnumType.STRING)
	var role: Role
)