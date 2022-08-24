package gg.dak.board_api.domain.account.util

import gg.dak.board_api.domain.account.data.dto.AccountDto
import gg.dak.board_api.domain.account.data.type.OperationType
import org.springframework.security.crypto.password.PasswordEncoder

class AccountProcessorImpl(
    private val passwordEncoder: PasswordEncoder
): AccountProcessor {
    override fun process(operation: OperationType, dto: AccountDto): AccountDto =
        when(operation) {
            OperationType.REGISTER -> processRegister(dto)
        }

    private fun processRegister(dto: AccountDto): AccountDto =
        encodePassword(dto).copy(idx = 0)

    private fun encodePassword(dto: AccountDto): AccountDto =
        if(dto.isPasswordEncoded) dto
        else dto.copy(password = passwordEncoder.encode(dto.password), isPasswordEncoded = true)
}