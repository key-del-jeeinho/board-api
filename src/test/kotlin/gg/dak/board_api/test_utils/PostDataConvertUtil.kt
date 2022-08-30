package gg.dak.board_api.test_utils

import gg.dak.board_api.domain.post.data.dto.PostDto
import gg.dak.board_api.domain.post.data.entity.Post

object PostDataConvertUtil {
    fun updatePost(entity: Post, updateContent: String) = Post(
        idx = entity.idx,
        writerIdx = entity.writerIdx,
        title = entity.title,
        content = updateContent,
        category = entity.category,
        board = entity.board,
    )

    fun toDto(entity: Post) = PostDto(
        idx = entity.idx,
        writerIdx = entity.writerIdx,
        title = entity.title,
        content = entity.content,
        category = entity.category,
        board = entity.board,
    )
}