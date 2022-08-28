package gg.dak.board_api.global.error.exception

interface GlobalException {
    fun getErrorMessage(): String
    fun getErrorDetails(): String
}