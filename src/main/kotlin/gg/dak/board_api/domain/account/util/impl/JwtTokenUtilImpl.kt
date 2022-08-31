package gg.dak.board_api.domain.account.util.impl

import gg.dak.board_api.domain.account.config.JwtProperties
import gg.dak.board_api.domain.account.util.JwtTokenUtil
import gg.dak.board_api.global.account.exception.JwtException
import gg.dak.board_api.global.common.util.DateUtil
import io.jsonwebtoken.Header
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import java.sql.Timestamp
import java.util.stream.Collectors

@Component
class JwtTokenUtilImpl(
    private val dateUtil: DateUtil,
    private val jwtProperties: JwtProperties
) : JwtTokenUtil {
    override fun generate(payload: Map<String, String>, expireSecond: Long): String =
        try {
            dateUtil.dateTimeNow().let {
                Jwts.builder()
                    .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                    .setIssuer(jwtProperties.issuer)
                    .setIssuedAt(Timestamp.valueOf(it))
                    .setExpiration(Timestamp.valueOf(it.plusSeconds(expireSecond)))
                    .addClaims(payload)
                    .signWith(SignatureAlgorithm.HS256, jwtProperties.secretKey)
                    .compact()
            }
        } catch(e: Throwable) {
            throw JwtException(e)
        }

    override fun decode(token: String): Map<String, String> =
        try {
            Jwts.parser()
                .setSigningKey(jwtProperties.secretKey)
                .parseClaimsJws(token)
                .body.entries.stream()
                .collect(Collectors.toMap(
                    { it.key },
                    { it.value.toString() }
                ))
        } catch(e: Throwable) {
            throw JwtException(e)
        }
}
