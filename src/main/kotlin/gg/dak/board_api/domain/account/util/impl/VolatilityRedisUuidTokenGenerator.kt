package gg.dak.board_api.domain.account.util.impl

import gg.dak.board_api.domain.account.data.enitty.UuidToken
import gg.dak.board_api.domain.account.data.event.UuidTokenDeleteEvent
import gg.dak.board_api.domain.account.exception.UnknownUuidTokenException
import gg.dak.board_api.domain.account.repository.UuidTokenRepository
import gg.dak.board_api.global.account.util.UuidTokenGenerator
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component
import java.util.*

//단 한번만 decode가능한 UUID토큰 Generator입니다.
//decode 시 decode된 token을 삭제합니다.
@Component("volatility")
class VolatilityRedisUuidTokenGenerator(
    private val uuidTokenRepository: UuidTokenRepository,
    private val applicationEventPublisher: ApplicationEventPublisher
): UuidTokenGenerator {
    override fun generate(payload: Map<String, String>, expireSecond: Long): String =
        UUID.randomUUID().toString()
            .let { UuidToken(it, payload, expireSecond) }
            .also { uuidTokenRepository.save(it) }
            .token

    override fun decode(token: String): Map<String, String> =
        uuidTokenRepository.findById(token)
            .also {
                if(it.isEmpty) throw UnknownUuidTokenException("존재하지 않는 UUID토큰입니다!", token)
                else uuidTokenRepository.deleteById(token)
            }.get().data
            .also { UuidTokenDeleteEvent(token, it).let { event -> applicationEventPublisher.publishEvent(event) } }
}