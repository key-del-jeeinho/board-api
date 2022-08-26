package gg.dak.board_api.domain.post.data.request

import gg.dak.board_api.domain.post.data.type.CategoryType

data class CreatePostRequest(
    val title: String,
    val content: String,
    val category: CategoryType
)