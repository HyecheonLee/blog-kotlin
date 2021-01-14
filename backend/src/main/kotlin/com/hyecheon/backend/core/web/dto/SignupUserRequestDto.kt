package com.hyecheon.backend.core.web.dto

import com.hyecheon.backend.core.domain.entity.*
import java.sql.*
import javax.persistence.*

/**
 * @author hyecheon
 * @email rainbow880616@gmail.com
 */
data class SignupUserRequestDto(
	var username: String,
	var name: String? = null,
	var email: String,
	var password: String,
)