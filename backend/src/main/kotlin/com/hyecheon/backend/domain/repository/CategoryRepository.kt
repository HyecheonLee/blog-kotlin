package com.hyecheon.backend.domain.repository

import com.hyecheon.backend.domain.entity.*
import org.springframework.data.jpa.repository.*

/**
 * @author hyecheon
 * @email rainbow880616@gmail.com
 */
interface CategoryRepository : JpaRepository<Category, Long> {
}