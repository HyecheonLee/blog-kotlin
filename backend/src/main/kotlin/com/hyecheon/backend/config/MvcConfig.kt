package com.hyecheon.backend.config

import com.hyecheon.backend.utils.*
import org.springframework.context.annotation.*
import org.springframework.web.servlet.config.annotation.*
import java.nio.file.*


/**
 * @author hyecheon
 * @email rainbow880616@gmail.com
 */
@Configuration
class MvcConfig : WebMvcConfigurer {
	override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
		exposeDirectory(UPLOAD_PATH, registry)
	}

	private fun exposeDirectory(dirName: String, registry: ResourceHandlerRegistry) {
		var dirName = dirName
		val uploadPath: String = Paths.get(dirName).toFile().absolutePath
		if (dirName.startsWith("../")) dirName = dirName.replace("../", "")
		registry.addResourceHandler("/$dirName/**").addResourceLocations("file:/$uploadPath/")
	}
}