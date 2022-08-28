package gg.dak.board_api.global.account.exception

import gg.dak.board_api.global.error.exception.GlobalException

class JwtException(private val e: Throwable) : RuntimeException(e), GlobalException {
    override fun getErrorMessage(): String = "JWT 토큰관련로직 처리중 오류가 발생하였습니다."
    override fun getErrorDetails(): String = e.message ?: "Unknown error"
}
