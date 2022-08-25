package gg.dak.board_api.global.security.service

import gg.dak.board_api.global.account.repository.AccountRepository
import gg.dak.board_api.global.account.exception.UnknownAccountException
import gg.dak.board_api.global.security.util.SecureAccountConverter
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(
    private val accountRepository: AccountRepository,
    private val secureAccountConverter: SecureAccountConverter
): UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails =
        accountRepository.findById(username)
            .let {
                if(it.isEmpty) throw UnknownAccountException("존재하지 않는 계정의 ID입니다! - $username")
                else it.get()
            }.let { secureAccountConverter.toUserDetails(it) }
}