package gg.dak.board_api.global.security.util

import gg.dak.board_api.domain.account.data.enitty.Account
import org.springframework.security.core.userdetails.UserDetails

interface SecureAccountConverter {
    fun toUserDetails(entity: Account): UserDetails
}
