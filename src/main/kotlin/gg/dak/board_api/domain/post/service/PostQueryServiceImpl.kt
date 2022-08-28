package gg.dak.board_api.domain.post.service

import gg.dak.board_api.domain.post.data.dto.PostQueryDto
import gg.dak.board_api.domain.post.data.type.BoardType
import gg.dak.board_api.domain.post.exception.UnknownPostException
import gg.dak.board_api.domain.post.repository.PostRepository
import gg.dak.board_api.domain.post.repository.PostViewCountRepository
import gg.dak.board_api.domain.post.util.PostConverter
import gg.dak.board_api.domain.post.util.PostQueryConverter
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class PostQueryServiceImpl(
    private val postRepository: PostRepository,
    private val postConverter: PostConverter,
    private val postViewCountRepository: PostViewCountRepository,
    private val postQueryConverter: PostQueryConverter
): PostQueryService {
    override fun findAllPost(pagination: PageRequest): Page<PostQueryDto> =
        postRepository.findBy(pagination)
            .map { postConverter.toDto(it) }
            .map { postViewCountRepository.findById(it.idx) to it } //조회수 정보, 게시글
            .map { (if(it.first.isEmpty) 0 else it.first.get().ips.size) to it.second} //조회수, 게시글
            .map { postQueryConverter.toQueryDto(it.first, it.second) }

    override fun findPostByIndex(idx: Long): PostQueryDto =
        postRepository.findById(idx)
            .let {
                if (it.isEmpty) throw UnknownPostException("존재하지 않는 게시글의 인덱스입니다! - $idx")
                else it.get()
            }.let { postConverter.toDto(it) }
            .let { postViewCountRepository.findById(it.idx) to it } //조회수 정보, 게시글
            .let { (if(it.first.isEmpty) 0 else it.first.get().ips.size) to it.second} //조회수, 게시글
            .let { postQueryConverter.toQueryDto(it.first, it.second) }

    override fun findAllPostByBoard(pagination: PageRequest, boardId: BoardType): Page<PostQueryDto> =
        postRepository.findAllByBoard(pagination, boardId)
            .map { postConverter.toDto(it) }
            .map { postViewCountRepository.findById(it.idx) to it } //조회수 정보, 게시글
            .map { (if(it.first.isEmpty) 0 else it.first.get().ips.size) to it.second} //조회수, 게시글
            .map { postQueryConverter.toQueryDto(it.first, it.second) }

    override fun findAllPostByWriterIdx(pagination: PageRequest, writerIdx: Long): Page<PostQueryDto> =
        postRepository.findAllByWriterIdx(pagination, writerIdx)
            .map { postConverter.toDto(it) }
            .map { postViewCountRepository.findById(it.idx) to it } //조회수 정보, 게시글
            .map { (if(it.first.isEmpty) 0 else it.first.get().ips.size) to it.second} //조회수, 게시글
            .map { postQueryConverter.toQueryDto(it.first, it.second) }
}