package gg.dak.board_api.domain.account.util.impl

import gg.dak.board_api.domain.account.data.dto.AccountDto
import gg.dak.board_api.domain.account.data.response.AccountQueryResponse
import gg.dak.board_api.domain.account.data.response.PageableAccountQueryResponse
import gg.dak.board_api.domain.account.util.AccountQueryConverter
import org.springframework.data.domain.PageImpl
import org.springframework.stereotype.Component

@Component
class AccountQueryConverterImpl: AccountQueryConverter {
    override fun toResponse(dto: AccountDto): AccountQueryResponse = AccountQueryResponse(idx = dto.idx, nickname = dto.nickname, id = dto.id)
    override fun toPageabelResponse(list: List<AccountQueryResponse>): PageableAccountQueryResponse = PageableAccountQueryResponse(PageImpl(list))
}