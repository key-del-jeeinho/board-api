package gg.dak.board_api.domain.post.repository

import gg.dak.board_api.domain.post.data.entity.DailyPostCount
import gg.dak.board_api.domain.post.data.type.BoardType
import org.springframework.data.repository.CrudRepository
import java.util.Optional

interface DailyPostCountRepository: CrudRepository<DailyPostCount, Long> {
    fun findByAccountIdxAndBoard(accountIdx: Long, board: BoardType): Optional<DailyPostCount>
    fun existsByAccountIdxAndBoard(accountIdx: Long, board: BoardType): Boolean
}