package gg.dak.board_api.domain.post.util

import gg.dak.board_api.domain.post.data.dto.PostDto
import gg.dak.board_api.domain.post.data.type.PostOperationType

interface PostProcessor {
    fun process(operation: PostOperationType, dto: PostDto): PostDto
}