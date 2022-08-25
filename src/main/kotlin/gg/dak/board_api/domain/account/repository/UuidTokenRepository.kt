package gg.dak.board_api.domain.account.repository

import gg.dak.board_api.domain.account.data.enitty.UuidToken
import org.springframework.data.repository.CrudRepository

interface UuidTokenRepository: CrudRepository<UuidToken, String> {
}