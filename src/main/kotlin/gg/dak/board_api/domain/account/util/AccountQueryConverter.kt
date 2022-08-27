package gg.dak.board_api.domain.account.util

import gg.dak.board_api.global.account.data.dto.AccountDto
import gg.dak.board_api.domain.account.data.response.AccountQueryResponse
import gg.dak.board_api.domain.account.data.response.PageableAccountQueryResponse

interface AccountQueryConverter {
    fun toResponse(dto: AccountDto): AccountQueryResponse
    fun toPageabelResponse(list: List<AccountQueryResponse>): PageableAccountQueryResponse

}
