package gg.dak.board_api.domain.post.data.response

import gg.dak.board_api.domain.post.data.type.BoardType
import gg.dak.board_api.domain.post.data.type.CategoryType

data class PostQueryResponse(
    val idx: Long,
    val writerIdx: Long,
    val title: String,
    val content: String,
    val category: CategoryType,
    val board: BoardType,
    val views: Int
)