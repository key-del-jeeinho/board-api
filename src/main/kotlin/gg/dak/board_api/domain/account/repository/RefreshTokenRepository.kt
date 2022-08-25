package gg.dak.board_api.domain.account.repository

import gg.dak.board_api.domain.account.data.enitty.RefreshToken
import org.springframework.data.repository.CrudRepository

interface RefreshTokenRepository: CrudRepository<RefreshToken, String> {
}