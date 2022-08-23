package gg.dak.board_api.global.common.exception

class PolicyValidationException(errorMessage: String, errorDetails: String) : RuntimeException("$errorMessage - $errorDetails")