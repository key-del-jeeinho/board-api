package gg.dak.board_api.domain.account.listener

import gg.dak.board_api.domain.account.data.event.RefreshTokenDeleteEvent
import gg.dak.board_api.domain.account.data.event.UuidTokenDeleteEvent
import gg.dak.board_api.domain.account.data.type.TokenType
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class UuidTokenDeleteEventListener(
    private val applicationEventPublisher: ApplicationEventPublisher
) {
    @EventListener(UuidTokenDeleteEvent::class)
    fun handle(event: UuidTokenDeleteEvent) {
        if(event.payload["type"].equals(TokenType.LOGIN_REFRESH.key))
            RefreshTokenDeleteEvent(event.payload["id"]!!) //나중에 validation 가능할지 생각해보기
                .let { applicationEventPublisher.publishEvent(it) }
    }
}