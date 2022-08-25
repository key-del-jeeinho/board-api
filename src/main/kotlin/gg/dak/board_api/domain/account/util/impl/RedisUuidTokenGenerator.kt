package gg.dak.board_api.domain.account.util.impl

import gg.dak.board_api.domain.account.data.enitty.UuidToken
import gg.dak.board_api.domain.account.exception.UnknownUuidTokenException
import gg.dak.board_api.domain.account.repository.UuidTokenRepository
import gg.dak.board_api.domain.account.util.UuidTokenGenerator
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

    override fun decode(token: String): Map<String, String> =
        uuidTokenRepository.findById(token)
        .let {
            if(it.isEmpty) throw UnknownUuidTokenException("존재하지 않는 UUID토큰입니다!", token)
            else it.get()
        }.data
}