package gg.dak.board_api.domain.account.service

import gg.dak.board_api.global.account.data.dto.AccountDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest

interface AccountQueryService {
    fun findAllAccount(pagination: PageRequest): Page<AccountDto>
    fun findAccountByIndex(idx: Long): AccountDto
    fun findAccountById(id: String): AccountDto
}
