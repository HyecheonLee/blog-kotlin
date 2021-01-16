package com.hyecheon.backend.core.config

import org.springframework.context.annotation.*
import org.springframework.data.domain.*
import org.springframework.data.jpa.repository.config.*
import org.springframework.security.core.*
import org.springframework.security.core.context.*
import java.util.*


/**
 * @author hyecheon
 * @email rainbow880616@gmail.com
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
class JpaConfiguration {
	@Bean
	fun auditorProvider(): AuditorAware<String> {
		return AuditorAware<String> {
			val authentication: Authentication? = SecurityContextHolder.getContext().authentication
			if (authentication?.principal == null || !authentication.isAuthenticated) {
				Optional.empty()
			} else {
				Optional.of(authentication.principal as String)
			}
		}
	}
}