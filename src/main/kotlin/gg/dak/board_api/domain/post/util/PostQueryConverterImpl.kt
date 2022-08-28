package gg.dak.board_api.domain.post.util

import gg.dak.board_api.domain.post.data.dto.PostDto
import gg.dak.board_api.domain.post.data.response.PageablePostQueryResponse
import gg.dak.board_api.domain.post.data.response.PostQueryResponse
import org.springframework.data.domain.PageImpl
import org.springframework.stereotype.Component

@Component
class PostQueryConverterImpl: PostQueryConverter {
    override fun toResponse(dto: PostDto): PostQueryResponse = PostQueryResponse(
        idx = dto.idx,
        writerIdx = dto.writerIdx,
        title = dto.title,
        content = dto.content,
        category = dto.category,
        board = dto.board,
    )

    override fun toPageableResponse(list: List<PostQueryResponse>): PageablePostQueryResponse = PageablePostQueryResponse(PageImpl(list))
}