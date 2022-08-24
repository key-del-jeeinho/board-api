package gg.dak.board_api.domain.account.util

import gg.dak.board_api.domain.account.data.dto.AccountDto
import gg.dak.board_api.domain.account.data.enitty.Account
import gg.dak.board_api.domain.account.data.request.RegisterRequest

interface AccountConverter {
    fun toDto(request: RegisterRequest): AccountDto
    fun toDto(entity: Account): AccountDto
    fun toEntity(dto: AccountDto): Account
}