package com.hyecheon.backend.repository

import com.hyecheon.backend.domain.*
import org.springframework.data.jpa.repository.*

/**
 * @author hyecheon
 * @email rainbow880616@gmail.com
 */
interface CategoryRepository : JpaRepository<Category, Long> {
}