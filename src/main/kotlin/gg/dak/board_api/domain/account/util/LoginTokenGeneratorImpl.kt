package gg.dak.board_api.domain.account.util

import gg.dak.board_api.domain.account.config.LoginProperties
import gg.dak.board_api.domain.account.data.dto.LoginTokenDto
import gg.dak.board_api.domain.account.data.type.TokenType

class LoginTokenGeneratorImpl(
    private val loginProperties: LoginProperties,
    private val jwtTokenGenerator: JwtTokenGenerator,
    private val uuidTokenGenerator: UuidTokenGenerator) : LoginTokenGenerator {
    override fun generate(id: String): LoginTokenDto =
        jwtTokenGenerator.generate( //accessToken을 발급합니다.
                mapOf(
                    "id" to id,
                    "type" to TokenType.LOGIN_ACCESS.key
                ), loginProperties.accessTokenProperties.expireSecond)
            .let { it to uuidTokenGenerator.generate( //refreshToken을 발급합니다.
                mapOf(
                    "id" to id,
                    "type" to TokenType.LOGIN_REFRESH.key,
                    "expiration" to false.toString()
                ), loginProperties.refreshTokenProperties.expireSecond)
            }.let { LoginTokenDto(it.first, it.second) }
}
