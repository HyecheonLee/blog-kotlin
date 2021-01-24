package com.hyecheon.backend.domain.entity

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
data class Blog(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	val id: Long? = null,

	@get:Size(min = 3, max = 160)
	var title: String? = null,

	var slug: String? = title?.slug(),

	@get:Size(min = 200, max = 200000)
	@Column(columnDefinition = "TEXT")
	var body: String? = null,

	@get:Max(1000)
	var excerpt: String? = null,

	@ManyToMany
	val categories: MutableList<Category> = mutableListOf(),

	@ManyToMany
	val tags: MutableList<Tag> = mutableListOf(),

	@OneToOne(cascade = [CascadeType.ALL], orphanRemoval = true)
	var photo: Files? = null,

	) : BaseEntityUser() {
	var mTitle: String? = null
		get() = if (field == null) "$title | $lastModifiedBy" else field
	var mDesc: String? = null
		get() = run {
			if (field == null) {
				field = body?.let { if (it.length > 160) it.substring(0, 160) else it.substring(0) }
			}
			field
		}
}