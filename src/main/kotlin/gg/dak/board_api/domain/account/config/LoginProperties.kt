package gg.dak.board_api.domain.account.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "account.login")
data class LoginProperties(
    val refreshTokenProperties: RefreshTokenProperties,
    val accessTokenProperties: AccessTokenProperties
) {
    data class RefreshTokenProperties(val expireSecond: Long)
    data class AccessTokenProperties(val expireSecond: Long)
}