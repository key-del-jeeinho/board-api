package gg.dak.board_api.global.common.advice

import gg.dak.board_api.global.common.exception.PolicyValidationException
import gg.dak.board_api.global.error.ErrorResponse
import gg.dak.board_api.global.error.ErrorStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class PolicyValidationAdvice {
    @ExceptionHandler(PolicyValidationException::class)
    fun handle(e: PolicyValidationException) =
        ErrorResponse(
            status = ErrorStatus.POLICY_VIOLATION,
            message = e.getErrorMessage(),
            details = e.getErrorDetails()
        ).let { ResponseEntity.badRequest().body(it) }
}