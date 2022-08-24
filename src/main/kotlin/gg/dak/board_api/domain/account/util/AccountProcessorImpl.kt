package gg.dak.board_api.domain.account.util

import gg.dak.board_api.domain.account.data.dto.AccountDto
import gg.dak.board_api.domain.account.data.type.OperationType
import org.springframework.security.crypto.password.PasswordEncoder

class AccountProcessorImpl(
    private val passwordEncoder: PasswordEncoder
): AccountProcessor {
    override fun process(operation: OperationType, dto: AccountDto): AccountDto {
        TODO()
    }
}