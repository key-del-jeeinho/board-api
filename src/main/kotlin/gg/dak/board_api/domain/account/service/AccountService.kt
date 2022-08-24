package gg.dak.board_api.domain.account.service

import gg.dak.board_api.domain.account.data.LoginTokenDto
import gg.dak.board_api.domain.account.data.dto.AccountDto

interface AccountService {
    fun register(dto: AccountDto): AccountDto
    fun login(dto: AccountDto): LoginTokenDto
}
