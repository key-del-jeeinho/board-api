package gg.dak.board_api.global.error

interface GlobalException {
    fun getMessage(): String
    fun getDetails(): String
}