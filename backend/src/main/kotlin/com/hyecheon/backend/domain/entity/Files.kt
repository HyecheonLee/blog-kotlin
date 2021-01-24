package com.hyecheon.backend.domain.entity

import com.hyecheon.backend.utils.*
import org.springframework.util.*
import org.springframework.web.multipart.*
import java.io.*
import java.util.*
import javax.persistence.*

/**
 * @author hyecheon
 * @email rainbow880616@gmail.com
 */
@Entity
data class Files(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	val id: Long? = null,
	val title: String?,
	val contentType: String?,
	val size: Long,
	val path: String
) {
	companion object {
		fun from(file: MultipartFile) = run {
			val uuid = UUID.randomUUID().toString()
			val resourcePath = "${UPLOAD_PATH}/${uuid}.${file.extension()}"
			FileCopyUtils.copy(file.bytes, File(resourcePath))
			Files(title = file.originalFilename, contentType = file.contentType, size = file.size, path = resourcePath)
		}
	}
}