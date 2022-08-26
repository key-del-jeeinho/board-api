package gg.dak.board_api.domain.post.util

import gg.dak.board_api.domain.post.data.dto.PostDto
import gg.dak.board_api.domain.post.data.entity.Post
import gg.dak.board_api.domain.post.data.request.CreatePostRequest
import gg.dak.board_api.domain.post.data.response.CreatePostResponse

interface PostConverter {
    fun toDto(request: CreatePostRequest, writerIdx: Long): PostDto
    fun toResponse(dto: PostDto): CreatePostResponse
    fun toEntity(dto: PostDto): Post
    fun toDto(entity: Post): PostDto

}
