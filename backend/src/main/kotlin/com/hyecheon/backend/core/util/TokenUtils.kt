import com.hyecheon.backend.core.domain.*
import com.hyecheon.backend.core.domain.entity.*
import io.jsonwebtoken.*
import org.slf4j.*
import java.security.*
import java.util.*
import javax.crypto.spec.*
import javax.xml.bind.*
import kotlin.collections.HashMap

/**
 * @author hyecheon
 * @email rainbow880616@gmail.com
 */
object TokenUtils {
	private const val secretKey = "ThisIsA_SecretKeyForJwtExample"
	private val log = LoggerFactory.getLogger(this::class.java)
	fun generateJwtToken(user: User): String {
		return Jwts.builder()
			.setSubject(user.email)
			.setHeader(createHeader())
			.setClaims(createClaims(user))
			.setExpiration(createExpireDate())
			.signWith(SignatureAlgorithm.HS256, createSigningKey())
			.compact()
	}


	fun isValidToken(token: String): Boolean {
		if (token.isBlank()) return false
		return try {
			val claims: Claims = getClaimsFormToken(token)
			log.info("expireTime :" + claims.expiration)
			log.info("email :" + claims["email"])
			log.info("role :" + claims["roles"])
			true
		} catch (exception: ExpiredJwtException) {
			log.error("Token Expired")
			false
		} catch (exception: JwtException) {
			log.error("Token Tampered")
			false
		} catch (exception: NullPointerException) {
			log.error("Token is null")
			false
		}
	}

	fun isValidTokenAndIsRefresh(token: String): Pair<Boolean, Boolean> {
		if (token.isBlank()) return (false to false)
		return try {
			val claims: Claims = getClaimsFormToken(token)
			log.info("expireTime :" + claims.expiration)
			log.info("email :" + claims["email"])
			log.info("role :" + claims["roles"])
			val future5min = Calendar.getInstance()
			future5min.add(Calendar.MINUTE, 5)
			if (claims.expiration.before(future5min.time)) {//토큰만료 시간 <  5분미래
				true to true
			} else {
				true to false
			}
		} catch (exception: ExpiredJwtException) {
			log.error("Token Expired")
			(false to false)
		} catch (exception: JwtException) {
			log.error("Token Tampered")
			(false to false)
		} catch (exception: NullPointerException) {
			log.error("Token is null")
			(false to false)
		}
	}

	fun refreshToken(token: String): String {
		val claims: Claims = getClaimsFormToken(token)
		return Jwts.builder()
			.setSubject(claims["email"] as String)
			.setHeader(createHeader())
			.setClaims(claims)
			.setExpiration(createExpireDate())
			.signWith(SignatureAlgorithm.HS256, createSigningKey())
			.compact()
	}

	fun getTokenFromHeader(header: String): String {
		val split = header.split(" ").takeIf { list -> list.size == 2 }
		return split?.get(1) ?: ""
	}

	private fun createExpireDate(): Date {
		// 토큰 만료시간은 30일로 설정
		val c = Calendar.getInstance()
		c.add(Calendar.MINUTE, 60 * 24 * 30)
		return c.time
	}

	private fun createHeader(): Map<String, Any> {
		val header: MutableMap<String, Any> = HashMap()
		header["typ"] = "JWT"
		header["alg"] = "HS256"
		header["regDate"] = System.currentTimeMillis()
		return header
	}

	private fun createClaims(user: User): Map<String, Any> {
		// 공개 클레임에 사용자의 이름과 이메일을 설정하여 정보를 조회할 수 있다.
		val claims: MutableMap<String, Any> = HashMap()
		claims["userId"] = user.id!!
		claims["email"] = user.email
		claims["roles"] = user.roles.map { it.role.name }
		return claims
	}

	private fun createSigningKey(): Key {
		val apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey)
		return SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.jcaName)
	}

	private fun getClaimsFormToken(token: String): Claims {
		return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(secretKey)).parseClaimsJws(token).body
	}

	public fun getUserEmailFromToken(token: String): String {
		val claims: Claims = getClaimsFormToken(token)
		return claims["email"] as String
	}

	public fun getRoleFromToken(token: String): List<String> = run {
		val claims: Claims = getClaimsFormToken(token)
		(claims["roles"] as MutableList<String>).map { role -> Role.create(role).value }
	}
}