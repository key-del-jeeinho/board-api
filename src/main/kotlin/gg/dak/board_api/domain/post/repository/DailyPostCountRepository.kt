package gg.dak.board_api.domain.post.repository

import gg.dak.board_api.domain.post.data.entity.DailyPostCount
import org.springframework.data.repository.CrudRepository

interface DailyPostCountRepository: CrudRepository<DailyPostCount, Long>