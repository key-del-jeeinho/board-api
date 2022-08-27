package gg.dak.board_api.domain.post.data.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash


@RedisHash("daily-post-count")
data class DailyPostCount(
    @Id val accountIdx: Long,
    val count: Int
)