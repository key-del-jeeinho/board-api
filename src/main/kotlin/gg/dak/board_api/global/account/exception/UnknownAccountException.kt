package gg.dak.board_api.global.account.exception

import gg.dak.board_api.global.error.exception.GlobalException

class UnknownAccountException(private val errorDetails: String) : RuntimeException(errorDetails), GlobalException {
    override fun getErrorMessage() = "존재하지 않는 계정입니다."
    override fun getErrorDetails() = errorDetails
}