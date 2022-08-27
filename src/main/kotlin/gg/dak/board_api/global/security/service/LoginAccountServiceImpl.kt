package gg.dak.board_api.global.security.service

import gg.dak.board_api.domain.account.util.AccountConverter
import gg.dak.board_api.global.account.data.dto.AccountDto
import gg.dak.board_api.global.account.repository.AccountRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service


@Service
class LoginAccountServiceImpl(
    private val accountRepository: AccountRepository,
    private val accountConverter: AccountConverter
): LoginAccountService {
    override fun getLoginAccount(): AccountDto =
        SecurityContextHolder.getContext().authentication.principal
            .let { it as UserDetails }.username
            .let { accountRepository.findById(it).get() } //이미 SecurityContextHolder 등록시점에서 ID 존재여부를 검증하였다.
            .let { accountConverter.toDto(it) }
}