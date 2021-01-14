package com.hyecheon.backend.core.exception

/**
 * @author hyecheon
 * @email rainbow880616@gmail.com
 */
class UsernameExistsException(username: String) : RuntimeException("[$username]은 이미 존재하는 이름 입니다.")