package gg.dak.board_api.global.security.util

import gg.dak.board_api.domain.account.data.enitty.Account
import gg.dak.board_api.global.security.data.UserDetailsImpl
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component

@Component
class SecureAccountConverterImpl: SecureAccountConverter {
    override fun toUserDetails(entity: Account): UserDetails = UserDetailsImpl(
        id = entity.id,
        encodedPassword = entity.encodedPassword,
        roles = mutableListOf("USER")
    )
}