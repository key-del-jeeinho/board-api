package gg.dak.board_api.domain.account.util

import gg.dak.board_api.domain.account.data.dto.AccountDto
import gg.dak.board_api.domain.account.data.request.RegisterRequest
import org.springframework.stereotype.Component

@Component
class AccountConverterImpl: AccountConverter {
    override fun toDto(request: RegisterRequest): AccountDto = AccountDto(idx = -1, nickname = request.nickname, id = request.id, password = request.password)
}