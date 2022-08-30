package gg.dak.board_api.global.security.service

import gg.dak.board_api.global.account.data.dto.AccountDto

interface LoginAccountService {
    fun getLoginAccount(): AccountDto
}
