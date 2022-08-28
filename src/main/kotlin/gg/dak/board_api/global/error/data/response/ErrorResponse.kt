package gg.dak.board_api.global.error.data.response

import gg.dak.board_api.global.error.data.type.ErrorStatusType

data class ErrorResponse(
    val status: ErrorStatusType,
    val message: String,
    val details: String
)