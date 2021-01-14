package com.hyecheon.backend.core.web.dto

import com.fasterxml.jackson.databind.*
import com.hyecheon.backend.core.domain.entity.*
import com.hyecheon.backend.core.util.*

/**
 * @author hyecheon
 * @email rainbow880616@gmail.com
 */
data class LoggedUserDto(
	val id: Long,
	val username: String,
	val name: String? = null,
	val email: String,
	val profile: String? = null,
	val about: String? = null,
	var roles: List<String> = listOf(),
	val photo: String? = null,
) {
	companion object {
		fun from(userEntity: UserEntity) = run {
			LoggedUserDto(
				id = userEntity.id!!, username = userEntity.username, name = userEntity.name, email = userEntity.email,
				profile = userEntity.profile, about = userEntity.about, roles = userEntity.roles(), photo = blobToBase64(userEntity.photo)
			)
		}
	}

	fun toJsonString(): String {
		return ObjectMapper().writeValueAsString(this)
	}
}