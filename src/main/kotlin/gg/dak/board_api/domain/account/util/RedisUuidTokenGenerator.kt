package gg.dak.board_api.domain.account.util

import gg.dak.board_api.domain.account.data.enitty.UuidToken
import gg.dak.board_api.domain.account.repository.UuidTokenRepository
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class RedisUuidTokenGenerator(
    private val uuidTokenRepository: UuidTokenRepository
): UuidTokenGenerator {
    override fun generate(payload: Map<String, String>, expireSecond: Long): String =
        UUID.randomUUID().toString()
            .let { UuidToken(it, payload, expireSecond) }
            .also { uuidTokenRepository.save(it) }
            .token

    override fun decode(token: String): Map<String, String> {
        TODO("Not yet implemented")
    }
}