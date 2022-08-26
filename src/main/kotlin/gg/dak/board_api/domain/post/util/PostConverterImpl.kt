package gg.dak.board_api.domain.post.util

import gg.dak.board_api.domain.post.data.dto.PostDto
import gg.dak.board_api.domain.post.data.request.CreatePostRequest
import gg.dak.board_api.domain.post.data.response.CreatePostResponse

class PostConverterImpl: PostConverter {
    override fun toDto(request: CreatePostRequest, writerIdx: Long): PostDto =
        PostDto(
            idx = -1,
            title = request.title,
            content = request.content,
            writerId = writerIdx
        )

    override fun toResponse(dto: PostDto): CreatePostResponse {
        TODO("Not yet implemented")
    }
}