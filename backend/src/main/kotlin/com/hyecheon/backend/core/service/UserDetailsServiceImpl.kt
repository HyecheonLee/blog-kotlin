package com.hyecheon.backend.core.service

import com.hyecheon.backend.core.domain.entity.User
import com.hyecheon.backend.core.domain.repository.*
import org.springframework.security.core.*
import org.springframework.security.core.authority.*
import org.springframework.security.core.userdetails.*
import org.springframework.stereotype.*


/**
 * @author hyecheon
 * @email rainbow880616@gmail.com
 */
@Service
class UserDetailsServiceImpl(
	val userRepository: UserRepository
) : UserDetailsService {
	override fun loadUserByUsername(email: String): UserDetails {
		val user = userRepository.findByEmail(email).orElseThrow { throw UsernameNotFoundException("[ $email ]존재 하지 않는 이메일 입니다.") }
		return getUserDetails(user)
	}

	fun getUserDetails(user: User): CustomUserDetails {
		return CustomUserDetails(user)
	}

	class CustomUserDetails(val user: User) : UserDetails {
		override fun getAuthorities(): List<GrantedAuthority> {
			return user.roles.map { SimpleGrantedAuthority(it.role.value) }
		}

		override fun getPassword(): String {
			return user.password
		}

		override fun getUsername(): String {
			return user.email
		}

		override fun isAccountNonExpired(): Boolean {
			return user.isEnable
		}

		override fun isAccountNonLocked(): Boolean {
			return user.isEnable
		}

		override fun isCredentialsNonExpired(): Boolean {
			return user.isEnable
		}

		override fun isEnabled(): Boolean {
			return user.isEnable
		}
	}
}