package gg.dak.board_api.domain.post.util

import gg.dak.board_api.domain.post.data.dto.PostDto
import gg.dak.board_api.domain.post.data.type.PostOperationType
import org.springframework.stereotype.Component

@Component
class PostProcessorImpl: PostProcessor {
    override fun process(operation: PostOperationType, dto: PostDto): PostDto =
        when(operation) {
            PostOperationType.CREATE -> dto.copy(idx = 0) //게시글 생성시, 인덱스를 0으로 초기화한다.
        }
}