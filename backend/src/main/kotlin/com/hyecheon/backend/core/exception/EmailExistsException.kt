package com.hyecheon.backend.core.exception

/**
 * @author hyecheon
 * @email rainbow880616@gmail.com
 */
class EmailExistsException(email: String) : RuntimeException("[$email]은 이미 존재하는 이메일 입니다.")