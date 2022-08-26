package gg.dak.board_api.domain.account.service

import gg.dak.board_api.domain.account.data.dto.AccountDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest

interface AccountQueryService {
    fun findAllAccount(pagination: PageRequest): Page<AccountDto>
}
