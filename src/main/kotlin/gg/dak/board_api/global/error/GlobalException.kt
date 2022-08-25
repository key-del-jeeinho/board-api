package gg.dak.board_api.global.error

interface GlobalException {
    fun getErrorMessage(): String
    fun getErrorDetails(): String
}