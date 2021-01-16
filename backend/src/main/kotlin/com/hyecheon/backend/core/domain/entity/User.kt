package com.hyecheon.backend.core.domain.entity

import java.sql.*
import javax.persistence.*

/**
 * @author hyecheon
 * @email rainbow880616@gmail.com
 */
@Entity
@Table(indexes = [Index(columnList = "email")])
data class User(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	var id: Long? = null,

	@Column(length = 32, nullable = false)
	var username: String,

	var name: String? = null,

	@Column(length = 32, nullable = false, unique = true)
	var email: String,

	var profile: String? = null,

	@Column(nullable = false)
	var password: String,

	var salt: String? = null,

	var about: String? = null,

	@OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	var roles: MutableSet<UserRole> = mutableSetOf(),

	@Lob
	val photo: String? = null,

	var resetPasswordLink: String? = null

) : BaseEntity() {
	fun roles(): List<String> {
		return roles.map { userRoleEntity -> userRoleEntity.role.name }
	}

}