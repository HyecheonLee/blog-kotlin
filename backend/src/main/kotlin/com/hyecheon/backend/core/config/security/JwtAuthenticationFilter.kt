package com.hyecheon.backend.core.config.security

import TokenUtils.generateJwtToken
import com.fasterxml.jackson.databind.*
import com.hyecheon.backend.core.service.*
import com.hyecheon.backend.core.util.AuthConstants.AUTH_HEADER
import com.hyecheon.backend.core.web.dto.*
import org.springframework.http.*
import org.springframework.security.authentication.*
import org.springframework.security.core.*
import org.springframework.security.core.authority.*
import org.springframework.security.core.context.*
import org.springframework.security.web.authentication.*
import javax.servlet.*
import javax.servlet.http.*

/**
 * @author hyecheon
 * @email rainbow880616@gmail.com
 */
class JwtAuthenticationFilter(authenticationManager: AuthenticationManager) : UsernamePasswordAuthenticationFilter() {

	init {
		setAuthenticationManager(authenticationManager)
		setFilterProcessesUrl("/api/user/signIn")
	}

	override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
		val requestBody = ObjectMapper().readValue(request.inputStream, Map::class.java)
		val authRequest = UsernamePasswordAuthenticationToken(requestBody["email"] as String, requestBody["password"] as String)
		setDetails(request, authRequest)
		SecurityContextHolder.getContext().authentication = authRequest
		return authenticationManager.authenticate(authRequest)
	}

	override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
		if (request is HttpServletRequest) {
			request.getHeader(AUTH_HEADER)?.let {
				val jwtToken = TokenUtils.getTokenFromHeader(it)
				if (isValid(jwtToken)) {
					jwtTokenAuth(jwtToken, request)
				}
			}
		}
		super.doFilter(request, response, chain)
	}

	private fun jwtTokenAuth(jwtToken: String, request: HttpServletRequest) {
		val email = TokenUtils.getUserEmailFromToken(jwtToken)
		val roles = TokenUtils.getRoleFromToken(jwtToken)
		val authRequest = UsernamePasswordAuthenticationToken(email, null, roles.map { role -> SimpleGrantedAuthority(role) })
		setDetails(request, authRequest)
		SecurityContextHolder.getContext().authentication = authRequest
	}

	override fun unsuccessfulAuthentication(request: HttpServletRequest, response: HttpServletResponse, failed: AuthenticationException) {
		response.sendError(HttpStatus.UNAUTHORIZED.value(), failed.message)
	}

	override fun successfulAuthentication(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain, authResult: Authentication) {
		val userDetails = authResult.principal as UserDetailsServiceImpl.CustomUserDetails
		val userEntity = userDetails.user
		val token = generateJwtToken(userEntity)
		response.addHeader(AUTH_HEADER, "Bearer $token")
		response.characterEncoding = "utf-8";
		response.contentType = "application/json";
		response.writer?.write(LoggedUserDto.from(userEntity).toJsonString())
	}

	private fun isValid(token: String?): Boolean {
		return token?.let { TokenUtils.isValidToken(it) } ?: false
	}

}