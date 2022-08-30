package gg.dak.board_api.domain.account.util

import gg.dak.board_api.domain.account.data.dto.LoginTokenDto

interface LoginTokenGenerator {
    fun generate(id: String): LoginTokenDto
}
