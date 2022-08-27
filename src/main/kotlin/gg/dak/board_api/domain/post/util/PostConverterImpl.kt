package gg.dak.board_api.domain.post.util

import gg.dak.board_api.domain.post.data.dto.PostDto
import gg.dak.board_api.domain.post.data.entity.Post
import gg.dak.board_api.domain.post.data.event.PostCreateEvent
import gg.dak.board_api.domain.post.data.event.PostDeleteEvent
import gg.dak.board_api.domain.post.data.request.CreatePostRequest
import gg.dak.board_api.domain.post.data.response.CreatePostResponse
import gg.dak.board_api.domain.post.data.response.DeletePostResponse
import gg.dak.board_api.domain.post.data.type.BoardType
import gg.dak.board_api.domain.post.data.type.CategoryType
import org.springframework.stereotype.Component

@Component
class PostConverterImpl: PostConverter {
    override fun toDto(idx: Long): PostDto = PostDto(
        idx = idx,
        title = "title",
        content = "content",
        writerIdx = -1,
        category = CategoryType.UNKNOWN,
        board = BoardType.UNKNOWN
    )

    override fun toDto(request: CreatePostRequest, writerIdx: Long): PostDto = PostDto(
            idx = -1,
            title = request.title,
            content = request.content,
            writerIdx = writerIdx,
            category = request.category,
            board = request.board
        )

    override fun toDto(entity: Post): PostDto = PostDto(
        idx = entity.idx,
        writerIdx = entity.writerIdx,
        title = entity.title,
        content = entity.content,
        category = entity.category,
        board = entity.board
    )

    override fun toCreateResponse(dto: PostDto): CreatePostResponse = CreatePostResponse(idx = dto.idx)
    override fun toDeleteResponse(dto: PostDto): DeletePostResponse = DeletePostResponse(deletedPostIdx = dto.idx)

    override fun toEntity(dto: PostDto): Post = Post(
        idx = dto.idx,
        writerIdx = dto.writerIdx,
        title = dto.title,
        content = dto.content,
        category = dto.category,
        board = dto.board
    )

    override fun toCreateEvent(dto: PostDto): PostCreateEvent = PostCreateEvent(
        idx = dto.idx,
        writerIdx = dto.writerIdx,
        content = dto.content,
        title = dto.title,
        board = dto.board,
        category = dto.category,
    )

    override fun toDeleteEvent(idx: Long): PostDeleteEvent {
        TODO("Not yet implemented")
    }
}