package gg.dak.board_api.domain.account.util.impl

import gg.dak.board_api.domain.account.data.dto.LoginTokenDto
import gg.dak.board_api.domain.account.data.dto.AccountDto
import gg.dak.board_api.domain.account.data.enitty.Account
import gg.dak.board_api.domain.account.data.request.LoginRequest
import gg.dak.board_api.domain.account.data.request.RegisterRequest
import gg.dak.board_api.domain.account.data.response.LoginResponse
import gg.dak.board_api.domain.account.util.AccountConverter
import org.springframework.stereotype.Component

@Component
class AccountConverterImpl: AccountConverter {
    override fun toDto(request: LoginRequest): AccountDto = AccountDto(idx = -1, nickname = "", id = request.id, password = request.password, isPasswordEncoded = false)
    override fun toDto(request: RegisterRequest): AccountDto = AccountDto(idx = -1, nickname = request.nickname, id = request.id, password = request.password, isPasswordEncoded = false)
    override fun toDto(entity: Account): AccountDto = AccountDto(idx = entity.idx, nickname = entity.nickName, id = entity.id, password = entity.encodedPassword, isPasswordEncoded = true)
    override fun toEntity(dto: AccountDto): Account = Account(idx = dto.idx, nickName =  dto.nickname, id = dto.id, encodedPassword = getEncodedPasswordOrEmpty(dto))
    override fun toResponse(loginToken: LoginTokenDto): LoginResponse = LoginResponse(accessToken = loginToken.accessToken, refreshToken = loginToken.refreshToken)
    private fun getEncodedPasswordOrEmpty(dto: AccountDto): String = if(dto.isPasswordEncoded) dto.password else ""
}