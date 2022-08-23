package gg.dak.board_api.domain.account.util

import gg.dak.board_api.domain.account.data.dto.AccountDto
import gg.dak.board_api.domain.account.data.enitty.Account
import gg.dak.board_api.domain.account.data.request.RegisterRequest
import org.springframework.stereotype.Component

@Component
class AccountConverterImpl: AccountConverter {
    override fun toDto(request: RegisterRequest): AccountDto = AccountDto(idx = -1, nickname = request.nickname, id = request.id, password = request.password, isPasswordEncoded = false)
    override fun toDto(entity: Account): AccountDto = AccountDto(idx = entity.idx, nickname = entity.nickName, id = entity.id, password = entity.encodedPassword, isPasswordEncoded = true)
    override fun toEntity(dto: AccountDto): Account = Account(idx = dto.idx, nickName =  dto.nickname, id = dto.id, encodedPassword = getEncodedPasswordOrEmpty(dto))
    private fun getEncodedPasswordOrEmpty(dto: AccountDto): String = if(dto.isPasswordEncoded) dto.password else ""
}