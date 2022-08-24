package gg.dak.board_api.domain.account.util

import gg.dak.board_api.domain.account.config.JwtProperties
import gg.dak.board_api.global.common.util.DateUtil
import io.jsonwebtoken.Header
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import java.sql.Timestamp

@Component
class JwtTokenGeneratorImpl(
    private val dateUtil: DateUtil,
    private val jwtProperties: JwtProperties
) : JwtTokenGenerator {
    override fun generate(payload: Map<String, String>, expireSecond: Long): String =
        dateUtil.dateTimeNow().let {
            Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer(jwtProperties.issuer)
                .setIssuedAt(Timestamp.valueOf(it))
                .setExpiration(Timestamp.valueOf(it.plusSeconds(expireSecond)))
                .setClaims(payload)
                .signWith(SignatureAlgorithm.HS256, jwtProperties.secretKey)
                .compact()
        }
}
