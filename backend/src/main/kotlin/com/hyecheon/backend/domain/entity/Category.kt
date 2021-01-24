package com.hyecheon.backend.domain.entity

import com.github.slugify.*
import com.hyecheon.backend.core.domain.entity.*
import com.hyecheon.backend.utils.*
import javax.persistence.*
import javax.validation.constraints.*


/**
 * @author hyecheon
 * @email rainbow880616@gmail.com
 */
@Entity
@Table(indexes = [Index(columnList = "slug")])
data class Category(
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	var id: Long?,

	@NotBlank(message = "이름은 필수 값입니다.")
	@Column(unique = true, length = 32)
	var name: String? = null,

//	@Column(unique = true)
	var slug: String? = name?.slug()
) : BaseEntity()