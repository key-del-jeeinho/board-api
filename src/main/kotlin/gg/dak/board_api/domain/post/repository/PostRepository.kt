package gg.dak.board_api.domain.post.repository

import gg.dak.board_api.domain.post.data.entity.Post
import gg.dak.board_api.domain.post.data.type.BoardType
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository: JpaRepository<Post, Long> {
    fun findBy(pageable: Pageable): Page<Post>
    fun findAllByBoard(pageable: Pageable, board: BoardType): Page<Post>
    fun findAllByWriterIdx(pageable: Pageable, writerIdx: Long): Page<Post>
}