package gg.dak.board_api.domain.post.util

import gg.dak.board_api.domain.post.data.dto.PostDto
import gg.dak.board_api.domain.post.data.entity.Post
import gg.dak.board_api.domain.post.data.event.PostCreateEvent
import gg.dak.board_api.domain.post.data.event.PostDeleteEvent
import gg.dak.board_api.domain.post.data.event.PostUpdateEvent
import gg.dak.board_api.domain.post.data.request.CreatePostRequest
import gg.dak.board_api.domain.post.data.request.UpdatePostRequest
import gg.dak.board_api.domain.post.data.response.CreatePostResponse
import gg.dak.board_api.domain.post.data.response.DeletePostResponse
import gg.dak.board_api.domain.post.data.response.UpdatePostResponse

interface PostConverter {
    fun toDto(idx: Long): PostDto
    fun toDto(postIdx: Long, request: UpdatePostRequest): PostDto
    fun toDto(request: CreatePostRequest, writerIdx: Long): PostDto
    fun toCreateResponse(dto: PostDto): CreatePostResponse
    fun toDeleteResponse(dto: PostDto): DeletePostResponse
    fun toUpdateResponse(dto: PostDto): UpdatePostResponse
    fun toEntity(dto: PostDto): Post
    fun toDto(entity: Post): PostDto
    fun toCreateEvent(dto: PostDto): PostCreateEvent
    fun toDeleteEvent(idx: Long): PostDeleteEvent
    fun toUpdateEvent(dto: PostDto): PostUpdateEvent
}
