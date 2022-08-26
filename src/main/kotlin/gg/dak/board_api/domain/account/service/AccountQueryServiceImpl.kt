package gg.dak.board_api.domain.account.service

import gg.dak.board_api.domain.account.data.dto.AccountDto
import gg.dak.board_api.domain.account.util.AccountConverter
import gg.dak.board_api.global.account.exception.UnknownAccountException
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

    override fun findAccountByIndex(idx: Long): AccountDto =
        accountRepository.findById(idx)
            .let {
                if(it.isEmpty) throw UnknownAccountException("존재하지 않는 계정의 인덱스입니다! - $idx")
                else it.get()
            }.let { accountConverter.toDto(it) }
}