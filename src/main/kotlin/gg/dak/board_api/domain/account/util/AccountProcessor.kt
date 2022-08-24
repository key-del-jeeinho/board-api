package gg.dak.board_api.domain.account.util

import gg.dak.board_api.domain.account.data.dto.AccountDto
import gg.dak.board_api.domain.account.data.type.OperationType

interface AccountProcessor {
    fun process(operation: OperationType, dto: AccountDto): AccountDto
}
