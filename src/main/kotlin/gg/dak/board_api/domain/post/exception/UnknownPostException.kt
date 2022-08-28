package gg.dak.board_api.domain.post.exception

import gg.dak.board_api.global.error.GlobalException

class UnknownPostException(private val errorDetails: String) : RuntimeException(errorDetails), GlobalException {
    override fun getErrorMessage() = "존재하지 않는 게시글입니다."
    override fun getErrorDetails() = errorDetails
}