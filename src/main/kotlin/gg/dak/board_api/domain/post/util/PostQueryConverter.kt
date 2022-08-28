package gg.dak.board_api.domain.post.util

import gg.dak.board_api.domain.post.data.dto.PostDto
import gg.dak.board_api.domain.post.data.response.PostQueryResponse

interface PostQueryConverter {
    fun toResponse(dto: PostDto): PostQueryResponse
}
