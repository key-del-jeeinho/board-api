package gg.dak.board_api.domain.account.data.event

data class UuidTokenDeleteEvent(
    val token: String,
    val payload: Map<String, String>
)