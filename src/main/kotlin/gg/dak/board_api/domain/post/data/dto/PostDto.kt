package gg.dak.board_api.domain.post.data.dto

data class PostDto(
    val idx: Long,
    val writerIdx: Long,
    val title: String,
    val content: String
)