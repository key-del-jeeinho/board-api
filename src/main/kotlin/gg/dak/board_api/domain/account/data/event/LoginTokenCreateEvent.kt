package gg.dak.board_api.domain.account.data.event

data class LoginTokenCreateEvent(
    val id: String,
    val accessToken: String,
    val refreshToken: String
)