package com.hyecheon.backend.core.util

import org.apache.tomcat.util.codec.binary.*
import java.sql.*

/**
 * @author hyecheon
 * @email rainbow880616@gmail.com
 */
fun blobToBase64(data: Blob?) = run {
	data?.run {
		getBytes(1L, length().toInt())?.let {
			Base64.encodeBase64String(it)
		}
	}
}