package com.hyecheon.backend.domain

import com.github.slugify.*
import com.hyecheon.backend.core.domain.entity.*
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
	var name: String?,

	@Column(unique = true)
	var slug: String? = null
) : BaseEntity() {
	init {
		val slg = Slugify().withLowerCase(true)
		slug = slg.slugify(name)
	}
}