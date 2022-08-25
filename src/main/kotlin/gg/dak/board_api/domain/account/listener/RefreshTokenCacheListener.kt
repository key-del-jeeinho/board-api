package gg.dak.board_api.domain.account.listener

import gg.dak.board_api.domain.account.config.LoginProperties
import gg.dak.board_api.domain.account.data.enitty.RefreshToken
import gg.dak.board_api.domain.account.data.event.LoginTokenCreateEvent
import gg.dak.board_api.domain.account.repository.RefreshTokenRepository
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class RefreshTokenCacheListener(
    private val refreshTokenRepository: RefreshTokenRepository,
    private val loginProperties: LoginProperties
) {
    @EventListener(LoginTokenCreateEvent::class)
    fun handle(event: LoginTokenCreateEvent) {
        loginProperties.refreshTokenProperties.expireSecond
            .let { RefreshToken(event.id, event.refreshToken, it) }
            .let { refreshTokenRepository.save(it) }
    }
}