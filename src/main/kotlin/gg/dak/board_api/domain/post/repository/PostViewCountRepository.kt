package gg.dak.board_api.domain.post.repository

import gg.dak.board_api.domain.post.data.entity.PostViewCount
import org.springframework.data.repository.CrudRepository

interface PostViewCountRepository: CrudRepository<PostViewCount, Long> {
}