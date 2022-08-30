package gg.dak.board_api.global.account.data.dto

data class AccountDto(
    val idx: Long,
    val nickname: String,
    val id: String,
    val password: String,
    val isPasswordEncoded: Boolean
)