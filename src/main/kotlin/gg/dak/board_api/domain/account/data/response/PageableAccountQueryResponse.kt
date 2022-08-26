package gg.dak.board_api.domain.account.data.response

import org.springframework.data.domain.Page

data class PageableAccountQueryResponse(
    val data: Page<AccountQueryResponse>
)