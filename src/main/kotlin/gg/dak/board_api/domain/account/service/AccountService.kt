package gg.dak.board_api.domain.account.service

import gg.dak.board_api.domain.account.data.dto.LoginTokenDto
import gg.dak.board_api.global.account.data.dto.AccountDto

interface AccountService {
    fun register(dto: AccountDto): AccountDto
    fun login(dto: AccountDto): LoginTokenDto
}
