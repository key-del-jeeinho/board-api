package gg.dak.board_api.domain.account.data.request

data class RegisterRequest(
    val nickname: String,
    val id: String,
    val password: String
)