package gg.dak.board_api.domain.post.data.event

import gg.dak.board_api.domain.post.data.type.BoardType
import gg.dak.board_api.domain.post.data.type.CategoryType

class PostQueryEvent(
    val idx: Long,
    val writerIdx: Long,
    val content: String,
    val title: String,
    val board: BoardType,
    val category: CategoryType,
    val ip: String
)