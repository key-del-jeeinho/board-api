package gg.dak.board_api.domain.post.service

import gg.dak.board_api.domain.post.data.dto.PostDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest

interface PostQueryService {
    fun findAllPost(pagination: PageRequest): Page<PostDto>
    fun findPostByIndex(idx: Long): PostDto
}
