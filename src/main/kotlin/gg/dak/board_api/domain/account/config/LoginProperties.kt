package gg.dak.board_api.domain.account.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("account.login")
data class LoginProperties(
    val refreshTokenProperties: RefreshTokenProperties,
    val accessTokenProperties: JwtTokenProperties
)

data class RefreshTokenProperties(val expireSecond: Long)
data class JwtTokenProperties(val expireSecond: Long)