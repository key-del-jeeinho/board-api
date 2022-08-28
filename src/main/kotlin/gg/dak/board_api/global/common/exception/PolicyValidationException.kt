package gg.dak.board_api.global.common.exception

import gg.dak.board_api.global.error.exception.GlobalException

class PolicyValidationException(private val errorMessage: String, private val errorDetails: String) : RuntimeException("$errorMessage - $errorDetails"),
    GlobalException {
    override fun getErrorMessage() = errorMessage
    override fun getErrorDetails() = errorDetails
}