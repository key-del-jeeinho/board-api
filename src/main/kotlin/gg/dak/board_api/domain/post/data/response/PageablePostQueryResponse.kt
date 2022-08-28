package gg.dak.board_api.domain.post.data.response

import org.springframework.data.domain.Page

data class PageablePostQueryResponse(
    val data: Page<PostQueryResponse>
)