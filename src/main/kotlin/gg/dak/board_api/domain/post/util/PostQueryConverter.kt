package gg.dak.board_api.domain.post.util

import gg.dak.board_api.domain.post.data.dto.PostDto
import gg.dak.board_api.domain.post.data.dto.PostQueryDto
import gg.dak.board_api.domain.post.data.event.PostQueryEvent
import gg.dak.board_api.domain.post.data.response.PageablePostSummeryQueryResponse
import gg.dak.board_api.domain.post.data.response.PostQueryResponse
import gg.dak.board_api.domain.post.data.response.PostSummeryQueryResponse

interface PostQueryConverter {
    fun toResponse(dto: PostQueryDto): PostQueryResponse
    fun toSummaryResponse(dto: PostQueryDto): PostSummeryQueryResponse
    fun toPageableResponse(list: List<PostSummeryQueryResponse>): PageablePostSummeryQueryResponse
    fun toEvent(dto: PostQueryDto, ip: String): PostQueryEvent
    fun toQueryDto(views: Int, dto: PostDto): PostQueryDto
}
