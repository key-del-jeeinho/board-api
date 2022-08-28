package gg.dak.board_api.domain.post.service

import gg.dak.board_api.domain.post.data.dto.PostDto
import gg.dak.board_api.domain.post.data.type.BoardType
import gg.dak.board_api.domain.post.exception.UnknownPostException
import gg.dak.board_api.domain.post.repository.PostRepository
import gg.dak.board_api.domain.post.util.PostConverter
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class PostQueryServiceImpl(
    private val postRepository: PostRepository,
    private val postConverter: PostConverter
): PostQueryService {
    override fun findAllPost(pagination: PageRequest): Page<PostDto> =
        postRepository.findBy(pagination)
            .map { postConverter.toDto(it) }

    override fun findPostByIndex(idx: Long): PostDto =
        postRepository.findById(idx)
            .let {
                if (it.isEmpty) throw UnknownPostException("존재하지 않는 게시글의 인덱스입니다! - $idx")
                else it.get()
            }.let { postConverter.toDto(it) }

    override fun findAllPostByBoard(pagination: PageRequest, boardId: BoardType): Page<PostDto> =
        postRepository.findAllByBoard(pagination, boardId)
            .map { postConverter.toDto(it) }

    override fun findAllPostByWriterIdx(pagination: PageRequest, writerIdx: Long): Page<PostDto> {
        TODO("Not yet implemented")
    }
}