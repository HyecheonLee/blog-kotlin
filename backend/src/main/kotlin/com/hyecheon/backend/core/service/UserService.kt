package com.hyecheon.backend.core.service

import TokenUtils
import com.hyecheon.backend.core.domain.*
import com.hyecheon.backend.core.domain.entity.*
import com.hyecheon.backend.core.domain.repository.*
import com.hyecheon.backend.core.exception.*
import com.hyecheon.backend.core.web.dto.*
import org.springframework.security.crypto.bcrypt.*
import org.springframework.stereotype.*

/**
 * @author hyecheon
 * @email rainbow880616@gmail.com
 */
@Service
class UserService(val userRepository: UserEntityRepository, val passwordEncoder: BCryptPasswordEncoder) {
	fun signUp(signupUserRequestDto: SignupUserRequestDto) = run {
		if (userRepository.existsByEmail(signupUserRequestDto.email)) {
			throw EmailExistsException(signupUserRequestDto.email)
		}
		if (userRepository.existsByEmail(signupUserRequestDto.username)) {
			throw EmailExistsException(signupUserRequestDto.username)
		}
		LoggedUserDto.from(
			userRepository.save(
				UserEntity(
					username = signupUserRequestDto.username,
					name = signupUserRequestDto.name,
					email = signupUserRequestDto.email,
					password = passwordEncoder.encode(signupUserRequestDto.password),
					roleEntities = mutableSetOf(UserRoleEntity(role = Role.USER))
				)
			)
		)
	}

	fun getJwtToken(loginUserRequestDto: LoginUserRequestDto): String {
		val userEntity = userRepository.findByEmail(loginUserRequestDto.email).orElseThrow { throw RuntimeException("에러") }
		if (passwordEncoder.matches(loginUserRequestDto.password, userEntity.password)) {
			return TokenUtils.generateJwtToken(userEntity)
		} else {
			throw RuntimeException("Password Validation")
		}
	}
}