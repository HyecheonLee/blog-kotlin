package com.hyecheon.backend.utils

import com.github.slugify.*
import org.springframework.web.multipart.*


/**
 * @author hyecheon
 * @email rainbow880616@gmail.com
 */
const val UPLOAD_PATH = "upload"
fun String.slug(): String? {
	val slg = Slugify().withLowerCase(true)
	return slg.slugify(this)
}

fun MultipartFile.extension() = run {
	this.originalFilename?.split(".")?.last()
}