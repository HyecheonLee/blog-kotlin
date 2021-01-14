package com.hyecheon.backend.core.exception

/**
 * @author hyecheon
 * @email rainbow880616@gmail.com
 */
class UserNotFoundException(email: String) : RuntimeException("$email 을 찾을 수 없습니다.")