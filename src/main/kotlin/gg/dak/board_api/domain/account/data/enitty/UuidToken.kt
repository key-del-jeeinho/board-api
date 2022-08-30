package gg.dak.board_api.domain.account.data.enitty

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import java.util.concurrent.TimeUnit

@RedisHash("uuid-token")
data class UuidToken(
    @Id val token: String,
    val data: Map<String, String>,
    @TimeToLive(unit = TimeUnit.SECONDS)
    val timeToLive: Long
)