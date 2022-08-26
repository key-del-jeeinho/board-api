package gg.dak.board_api.domain.account.service

import gg.dak.board_api.domain.account.data.dto.AccountDto
import gg.dak.board_api.domain.account.util.AccountConverter
import gg.dak.board_api.global.account.repository.AccountRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class AccountQueryServiceImpl(
    private val accountRepository: AccountRepository,
    private val accountConverter: AccountConverter
): AccountQueryService {
    override fun findAllAccount(pagination: PageRequest): Page<AccountDto> =
        accountRepository.findBy(pagination)
            .map { accountConverter.toDto(it) }

    override fun findAccountByIndex(idx: Long): AccountDto {
        TODO("Not yet implemented")
    }
}