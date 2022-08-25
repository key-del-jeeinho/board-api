package gg.dak.board_api.domain.account.service

import gg.dak.board_api.domain.account.data.dto.LoginTokenDto

interface RefreshLoginService {
    fun refreshLogin(refreshToken: String): LoginTokenDto
}
