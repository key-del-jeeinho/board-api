package gg.dak.board_api.domain.account.listener

import gg.dak.board_api.domain.account.data.event.LoginTokenCreateEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class RefreshTokenCacheListener {
    @EventListener(LoginTokenCreateEvent::class)
    fun handle(event: LoginTokenCreateEvent) {

    }
}