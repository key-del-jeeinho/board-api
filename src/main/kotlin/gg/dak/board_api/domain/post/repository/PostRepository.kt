package gg.dak.board_api.domain.post.repository

import gg.dak.board_api.domain.post.data.entity.Post
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository: JpaRepository<Post, Long> {
}