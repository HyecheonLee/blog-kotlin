package com.hyecheon.backend.core.domain

/**
 * @author hyecheon
 * @email rainbow880616@gmail.com
 */
public enum class Role(val value: String) {
	ADMIN("ROLE_ADMIN"), USER("ROLE_USER"), ANONYMOUS("ROLE_ANONYMOUS");

	companion object {
		fun create(role: String) = run {
			when {
				role.toUpperCase() == "ADMIN" -> ADMIN
				role.toUpperCase() == "USER" -> USER
				else -> ANONYMOUS
			}
		}
	}
}