package com.hyecheon.backend.core.service

import com.hyecheon.backend.core.domain.entity.*
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
	val userEntityRepository: UserEntityRepository
) : UserDetailsService {
	override fun loadUserByUsername(email: String): UserDetails {
		val user = userEntityRepository.findByEmail(email).orElseThrow { throw UsernameNotFoundException("[ $email ]존재 하지 않는 이메일 입니다.") }
		return getUserDetails(user)
	}

	fun getUserDetails(user: UserEntity): CustomUserDetails {
		return CustomUserDetails(user)
	}

	class CustomUserDetails(val userEntity: UserEntity) : UserDetails {
		override fun getAuthorities(): List<GrantedAuthority> {
			return userEntity.roleEntities.map { SimpleGrantedAuthority(it.role.value) }
		}

		override fun getPassword(): String {
			return userEntity.password
		}

		override fun getUsername(): String {
			return userEntity.email
		}

		override fun isAccountNonExpired(): Boolean {
			return userEntity.isEnable
		}

		override fun isAccountNonLocked(): Boolean {
			return userEntity.isEnable
		}

		override fun isCredentialsNonExpired(): Boolean {
			return userEntity.isEnable
		}

		override fun isEnabled(): Boolean {
			return userEntity.isEnable
		}
	}
}