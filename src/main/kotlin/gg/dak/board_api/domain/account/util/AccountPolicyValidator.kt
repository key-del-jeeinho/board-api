package gg.dak.board_api.domain.account.util

import gg.dak.board_api.global.account.data.dto.AccountDto
import gg.dak.board_api.domain.account.data.type.OperationType

interface AccountPolicyValidator {
    fun validate(operation: OperationType, dto: AccountDto)

}
