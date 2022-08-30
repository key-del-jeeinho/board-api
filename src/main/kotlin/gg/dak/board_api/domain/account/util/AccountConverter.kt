package gg.dak.board_api.domain.account.util

import gg.dak.board_api.domain.account.data.dto.LoginTokenDto
import gg.dak.board_api.global.account.data.dto.AccountDto
import gg.dak.board_api.domain.account.data.enitty.Account
import gg.dak.board_api.domain.account.data.request.LoginRequest
import gg.dak.board_api.domain.account.data.request.RegisterRequest
import gg.dak.board_api.domain.account.data.response.LoginResponse

interface AccountConverter {
    fun toDto(request: LoginRequest): AccountDto
    fun toDto(request: RegisterRequest): AccountDto
    fun toDto(entity: Account): AccountDto
    fun toEntity(dto: AccountDto): Account
    fun toResponse(loginToken: LoginTokenDto): LoginResponse
}