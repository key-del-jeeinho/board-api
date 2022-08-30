package gg.dak.board_api.domain.account.util.impl

import gg.dak.board_api.domain.account.config.LoginProperties
import gg.dak.board_api.domain.account.data.dto.LoginTokenDto
import gg.dak.board_api.domain.account.data.event.LoginTokenCreateEvent
import gg.dak.board_api.domain.account.data.type.TokenType
import gg.dak.board_api.domain.account.repository.RefreshTokenRepository
import gg.dak.board_api.domain.account.repository.UuidTokenRepository
import gg.dak.board_api.domain.account.util.JwtTokenGenerator
import gg.dak.board_api.domain.account.util.LoginTokenGenerator
import gg.dak.board_api.global.account.util.UuidTokenGenerator
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class LoginTokenGeneratorImpl(
    private val loginProperties: LoginProperties,
    private val jwtTokenGenerator: JwtTokenGenerator,
    @Qualifier("volatility") private val uuidTokenGenerator: UuidTokenGenerator,
    private val applicationEventPublisher: ApplicationEventPublisher,
    private val uuidTokenRepository: UuidTokenRepository,
    private val refreshTokenRepository: RefreshTokenRepository
) : LoginTokenGenerator {
    override fun generate(id: String): LoginTokenDto =
        refreshTokenRepository.findById(id) //기존에 재발급토큰이 존재하였는지 검사합니다.
            .let { if(it.isPresent) uuidTokenRepository.deleteById(it.get().token) } //기존에 재발금토큰이 존재하였다면, 이를 제거합니다.
            .let { jwtTokenGenerator.generate( //accessToken을 발급합니다.
                mapOf(
                    "id" to id,
                    "type" to TokenType.LOGIN_ACCESS.key
                ), loginProperties.accessTokenProperties.expireSecond) }
            .let { it to uuidTokenGenerator.generate( //refreshToken을 발급합니다.
                mapOf(
                    "id" to id,
                    "type" to TokenType.LOGIN_REFRESH.key,
                    "expiration" to false.toString()
                ), loginProperties.refreshTokenProperties.expireSecond)
            }.let { LoginTokenDto(it.first, it.second) }
            //비즈니스로직 수행 후 loginToken 생성 이벤트를 발행합니다.
            .also { applicationEventPublisher.publishEvent(LoginTokenCreateEvent(id, it.accessToken, it.refreshToken)) }
}
