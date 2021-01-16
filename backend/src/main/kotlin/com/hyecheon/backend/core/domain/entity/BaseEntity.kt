package com.hyecheon.backend.core.domain.entity

import org.springframework.data.annotation.*
import org.springframework.data.jpa.domain.support.*
import java.time.*
import javax.persistence.*


/**
 * @author hyecheon
 * @email rainbow880616@gmail.com
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity(

	@CreatedDate
	@Column(updatable = false)
	var createdDate: LocalDateTime = LocalDateTime.now(),

	@LastModifiedDate
	var lastModifiedDate: LocalDateTime = LocalDateTime.now(),

	@Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT true")
	var isEnable: Boolean = true,
)