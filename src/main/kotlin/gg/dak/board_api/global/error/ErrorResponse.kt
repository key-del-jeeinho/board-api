package gg.dak.board_api.global.error

data class ErrorResponse(
    val status: ErrorStatus,
    val message: String,
    val details: String
)