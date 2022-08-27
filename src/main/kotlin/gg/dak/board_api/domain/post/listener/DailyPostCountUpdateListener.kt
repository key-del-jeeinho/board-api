package gg.dak.board_api.domain.post.listener

import gg.dak.board_api.domain.post.data.entity.DailyPostCount
import gg.dak.board_api.domain.post.data.event.PostCreateEvent
import gg.dak.board_api.domain.post.repository.DailyPostCountRepository
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class DailyPostCountUpdateListener(
    private val dailyPostCountRepository: DailyPostCountRepository
) {
    @EventListener(PostCreateEvent::class)
    fun handle(e: PostCreateEvent) {
        dailyPostCountRepository.existsByAccountIdx(e.writerIdx)
            .let { isExists ->
                if(isExists) dailyPostCountRepository.findByAccountIdx(e.writerIdx).get()
                else DailyPostCount(e.writerIdx, 0)
            }.let { it.copy(count = it.count+1) }
            .let { dailyPostCountRepository.save(it) }
    }
}