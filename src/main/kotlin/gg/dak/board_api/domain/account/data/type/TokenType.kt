package gg.dak.board_api.domain.account.data.type

enum class TokenType(val key: String) {
    LOGIN_ACCESS("login-access"), LOGIN_REFRESH("login-refresh")
}