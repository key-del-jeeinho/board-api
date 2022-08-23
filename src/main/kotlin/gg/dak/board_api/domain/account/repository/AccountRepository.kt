package gg.dak.board_api.domain.account.repository

import gg.dak.board_api.domain.account.data.enitty.Account
import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository: JpaRepository<Account, Long>
