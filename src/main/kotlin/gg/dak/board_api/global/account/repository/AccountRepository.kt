package gg.dak.board_api.global.account.repository

import gg.dak.board_api.domain.account.data.enitty.Account
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface AccountRepository: JpaRepository<Account, Long> {
    fun findById(id: String): Optional<Account>
}