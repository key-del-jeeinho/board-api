package gg.dak.board_api.domain.post.util

import gg.dak.board_api.domain.post.data.dto.PostDto
import gg.dak.board_api.domain.post.data.event.PostQueryEvent
import gg.dak.board_api.domain.post.data.response.PageablePostSummeryQueryResponse
import gg.dak.board_api.domain.post.data.response.PostQueryResponse
import gg.dak.board_api.domain.post.data.response.PostSummeryQueryResponse

interface PostQueryConverter {
    fun toResponse(dto: PostDto): PostQueryResponse
    fun toSummaryResponse(dto: PostDto): PostSummeryQueryResponse
    fun toPageableResponse(list: List<PostSummeryQueryResponse>): PageablePostSummeryQueryResponse
    fun toEvent(dto: PostDto, ip: String): PostQueryEvent
}
