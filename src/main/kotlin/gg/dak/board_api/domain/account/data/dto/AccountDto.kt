package gg.dak.board_api.domain.account.data.dto

data class AccountDto(
    val idx: Long,
    val nickname: String,
    val id: String,
    val password: String,
    val isPasswordEncoded: Boolean
)