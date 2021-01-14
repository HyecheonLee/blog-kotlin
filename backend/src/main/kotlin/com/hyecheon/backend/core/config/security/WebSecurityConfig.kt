package com.hyecheon.backend.core.config.security

import com.hyecheon.backend.core.service.*
import org.springframework.boot.autoconfigure.security.servlet.*
import org.springframework.context.annotation.*
import org.springframework.security.config.annotation.authentication.builders.*
import org.springframework.security.config.annotation.web.builders.*
import org.springframework.security.config.annotation.web.configuration.*
import org.springframework.security.config.http.*
import org.springframework.security.crypto.bcrypt.*
import org.springframework.security.web.authentication.*
import org.springframework.web.cors.*


/**
 * @author hyecheon
 * @email rainbow880616@gmail.com
 */
@EnableWebSecurity
class WebSecurityConfig : WebSecurityConfigurerAdapter() {

	// 정적 자원에 대해서는 Security 설정을 적용하지 않음.
	override fun configure(web: WebSecurity) {
		web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations())
	}

	@Throws(Exception::class)
	override fun configure(http: HttpSecurity) {
		http.cors().configurationSource(corsConfigurationSource())
			.and()
			.csrf().disable()
			// 토큰을 활용하면 세션이 필요 없으므로 STATELESS로 설정하여 Session을 사용하지 않는다.
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeRequests() // 토큰을 활용하는 경우 모든 요청에 대해 접근이 가능하도록 함
			.antMatchers("/api/v1/**").hasRole("USER")
			.antMatchers("/api/user/signUp").permitAll()
			.anyRequest().permitAll()
			.and() // form 기반의 로그인에 대해 비활성화 한다.
			.formLogin()
			.disable()
			.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)
	}

	@Bean
	fun corsConfigurationSource(): CorsConfigurationSource {
		val configuration = CorsConfiguration()
		configuration.addAllowedOrigin("http://localhost:3000")
		configuration.addAllowedHeader("*")
		configuration.addAllowedMethod("*")
		configuration.allowCredentials = true
		configuration.addExposedHeader("*")
		configuration.exposedHeaders = listOf(
			"Access-Control-Allow-Headers",
			"Authorization",
			"X-Frame-Options",
			"*",
			"x-xsrf-token",
			"Access-Control-Allow-Headers",
			"Origin",
			"Accept",
			"X-Requested-With",
			"Content-Type",
			"Access-Control-Request-Method",
			"Access-Control-Request-Headers"
		)
		val source = UrlBasedCorsConfigurationSource()
		source.registerCorsConfiguration("/**", configuration)
		return source
	}

	@Bean
	fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
		return BCryptPasswordEncoder()
	}

	@Bean
	fun jwtAuthenticationFilter(): JwtAuthenticationFilter = run {
		JwtAuthenticationFilter(authenticationManager())
	}

	@Bean
	fun jwtAuthenticationProvider(userDetailsService: UserDetailsServiceImpl? = null): JwtAuthenticationProvider {
		return JwtAuthenticationProvider(userDetailsService!!, bCryptPasswordEncoder())
	}

	override fun configure(authenticationManagerBuilder: AuthenticationManagerBuilder) {
		authenticationManagerBuilder.authenticationProvider(jwtAuthenticationProvider())
	}
}