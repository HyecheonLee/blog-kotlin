package com.hyecheon.backend.core.config.security

import org.springframework.security.authentication.*
import org.springframework.security.core.*
import org.springframework.security.core.userdetails.*
import org.springframework.security.crypto.bcrypt.*


/**
 * @author hyecheon
 * @email rainbow880616@gmail.com
 */
class JwtAuthenticationProvider(
	private val userDetailsService: UserDetailsService,
	private val passwordEncoder: BCryptPasswordEncoder
) : AuthenticationProvider {

	override fun authenticate(authentication: Authentication): Authentication {
		val token = authentication as UsernamePasswordAuthenticationToken
		// AuthenticaionFilter에서 생성된 토큰으로부터 아이디와 비밀번호를 조회함
		val email = token.name
		val password = token.credentials as String
		val userDetails = userDetailsService.loadUserByUsername(email)
		if (!passwordEncoder.matches(password, userDetails.password)) {
			throw  BadCredentialsException("[ $email ]의 비밀번호가 틀렸습니다.")
		}
		return UsernamePasswordAuthenticationToken(userDetails, password, userDetails.authorities)
	}

	override fun supports(authentication: Class<*>): Boolean {
		return authentication == UsernamePasswordAuthenticationToken::class.java
	}
}