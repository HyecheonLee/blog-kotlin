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
class UserService(val userRepository: UserRepository, val passwordEncoder: BCryptPasswordEncoder) {
	fun signUp(signupUserRequestDto: SignupUserRequestDto) = run {
		if (userRepository.existsByEmail(signupUserRequestDto.email)) {
			throw EmailExistsException(signupUserRequestDto.email)
		}
		if (userRepository.existsByEmail(signupUserRequestDto.username)) {
			throw EmailExistsException(signupUserRequestDto.username)
		}
		LoggedUserDto.from(
			userRepository.save(
				User(
					username = signupUserRequestDto.username,
					name = signupUserRequestDto.name,
					email = signupUserRequestDto.email,
					password = passwordEncoder.encode(signupUserRequestDto.password),
					roles = mutableSetOf(UserRole(role = Role.USER))
				)
			)
		)
	}

	fun findUserInfo(email: String) = run {
		userRepository.findByEmail(email).orElseThrow { throw UserNotFoundException("이메일 [$email] 을 확인해 주세요") }
	}

	fun findUserInfo(id: Long) = run {
		userRepository.findById(id).orElseThrow { throw UserNotFoundException("존재하지 않는 사용자 입니다.") }
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