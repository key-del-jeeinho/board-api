package gg.dak.board_api.domain.account.exception

import gg.dak.board_api.global.error.GlobalException

class UnknownUuidTokenException(private val errorMessage: String, val token: String) : RuntimeException("$errorMessage - $token"), GlobalException {
    override fun getErrorMessage() = errorMessage
    override fun getErrorDetails() = "$token 은 존재하지않는 UUID토큰입니다."
}