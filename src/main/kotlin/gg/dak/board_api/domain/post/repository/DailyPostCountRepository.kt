package gg.dak.board_api.domain.post.repository

import gg.dak.board_api.domain.post.data.entity.DailyPostCount
import org.springframework.data.repository.CrudRepository
import java.util.*

interface DailyPostCountRepository: CrudRepository<DailyPostCount, Long> {
    fun findByAccountIdx(accountIdx: Long): Optional<DailyPostCount>
    fun existsByAccountIdx(accountIdx: Long): Boolean
}