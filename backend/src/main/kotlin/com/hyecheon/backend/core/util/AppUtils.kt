package com.hyecheon.backend.core.util

import java.sql.*
import java.util.*

/**
 * @author hyecheon
 * @email rainbow880616@gmail.com
 */
fun blobToBase64(data: Blob?) = run {
	data?.run {
		getBytes(1L, length().toInt())?.let {
			val encoder = Base64.getEncoder()
			encoder.encode(it).decodeToString()
		}
	}
}

fun base64ToBlob(data: String): ByteArray = run {
	val decoder = Base64.getDecoder()
	decoder.decode(data.encodeToByteArray())
}