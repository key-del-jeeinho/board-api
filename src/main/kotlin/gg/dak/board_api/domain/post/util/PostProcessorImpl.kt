package gg.dak.board_api.domain.post.util

import gg.dak.board_api.domain.post.data.dto.PostDto
import gg.dak.board_api.domain.post.data.type.PostOperationType
import gg.dak.board_api.domain.post.repository.PostRepository
import org.springframework.stereotype.Component

@Component
class PostProcessorImpl(
    private val postRepository: PostRepository,
    private val postConverter: PostConverter
): PostProcessor {
    override fun process(operation: PostOperationType, dto: PostDto): PostDto =
        when(operation) {
            PostOperationType.CREATE -> dto.copy(idx = 0) //게시글 생성시, 인덱스를 0으로 초기화한다.
            PostOperationType.DELETE -> dto //게시글 삭제시, 어떠한 처리 없이, 그대로 반환한다.
            PostOperationType.UPDATE ->
                postRepository.findById(dto.idx).get()
                    .let { postConverter.toDto(it) }
                    .copy(content = dto.content)
        }
}