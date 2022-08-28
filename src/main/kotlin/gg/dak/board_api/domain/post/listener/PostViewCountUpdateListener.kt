package gg.dak.board_api.domain.post.listener

import gg.dak.board_api.domain.post.data.entity.PostViewCount
import gg.dak.board_api.domain.post.data.event.PostQueryEvent
import gg.dak.board_api.domain.post.repository.PostViewCountRepository
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class PostViewCountUpdateListener(
    private val postViewCountRepository: PostViewCountRepository
) {
    @EventListener(PostQueryEvent::class)
    fun handle(e: PostQueryEvent) {
        postViewCountRepository.existsById(e.idx)
            .let { isExists ->
                if(isExists) postViewCountRepository.findById(e.idx).get()
                else PostViewCount(e.idx, emptySet())
            }.let { it.copy(ips = it.ips + e.ip) }
            .let { postViewCountRepository.save(it) }
    }
    
    //TODO PostDeleteEvent 받아서 조회수 정보 제거
}