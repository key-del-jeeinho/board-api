package gg.dak.board_api.domain.post.util

import gg.dak.board_api.domain.post.data.dto.PostDto
import gg.dak.board_api.domain.post.data.event.PostQueryEvent
import gg.dak.board_api.domain.post.data.response.PageablePostSummeryQueryResponse
import gg.dak.board_api.domain.post.data.response.PostQueryResponse
import gg.dak.board_api.domain.post.data.response.PostSummeryQueryResponse
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

    override fun toSummaryResponse(dto: PostDto): PostSummeryQueryResponse = PostSummeryQueryResponse(
        idx = dto.idx,
        writerIdx = dto.writerIdx,
        title = dto.title,
        category = dto.category,
        board = dto.board,
    )

    override fun toPageableResponse(list: List<PostSummeryQueryResponse>): PageablePostSummeryQueryResponse = PageablePostSummeryQueryResponse(PageImpl(list))
    override fun toEvent(dto: PostDto, ip: String): PostQueryEvent {
        TODO("Not yet implemented")
    }
}