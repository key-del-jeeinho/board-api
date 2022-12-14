package gg.dak.board_api.domain.post.service

import gg.dak.board_api.domain.post.data.dto.PostDto

interface PostService {
    fun createPost(dto: PostDto): PostDto
    fun deletePost(dto: PostDto): PostDto
    fun updatePost(dto: PostDto): PostDto
}
