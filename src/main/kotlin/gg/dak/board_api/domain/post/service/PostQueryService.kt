package gg.dak.board_api.domain.post.service

import gg.dak.board_api.domain.post.data.dto.PostQueryDto
import gg.dak.board_api.domain.post.data.type.BoardType
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest

interface PostQueryService {
    fun findAllPost(pagination: PageRequest): Page<PostQueryDto>
    fun findPostByIndex(idx: Long): PostQueryDto
    fun findAllPostByBoard(pagination: PageRequest, boardId: BoardType): Page<PostQueryDto>
    fun findAllPostByWriterIdx(pagination: PageRequest, writerIdx: Long): Page<PostQueryDto>
}
