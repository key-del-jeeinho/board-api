package gg.dak.board_api.domain.post.data.entity

import gg.dak.board_api.domain.post.data.type.BoardType
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed


@RedisHash("daily-post-count")
data class DailyPostCount(
    @Indexed val accountIdx: Long,
    val count: Int,
    @Indexed val board: BoardType,
    @Id val id: Long = accountIdx * 10 + board.ordinal
)