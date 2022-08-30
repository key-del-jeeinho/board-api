package gg.dak.board_api.domain.account.util.impl

import gg.dak.board_api.global.account.data.dto.AccountDto
import gg.dak.board_api.domain.account.data.type.OperationType
import gg.dak.board_api.domain.account.util.AccountProcessor
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class AccountProcessorImpl(
    private val passwordEncoder: PasswordEncoder
): AccountProcessor {
    override fun process(operation: OperationType, dto: AccountDto): AccountDto =
        when(operation) {
            OperationType.REGISTER -> processRegister(dto)
            OperationType.LOGIN -> dto
        }

    private fun processRegister(dto: AccountDto): AccountDto =
        encodePassword(dto).copy(idx = 0)

    private fun encodePassword(dto: AccountDto): AccountDto =
        if(dto.isPasswordEncoded) dto
        else dto.copy(password = passwordEncoder.encode(dto.password), isPasswordEncoded = true)
}