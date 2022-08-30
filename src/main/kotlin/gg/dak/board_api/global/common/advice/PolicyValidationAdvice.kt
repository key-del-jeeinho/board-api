package gg.dak.board_api.global.common.advice

import gg.dak.board_api.global.common.exception.PolicyValidationException
import gg.dak.board_api.global.error.data.response.ErrorResponse
import gg.dak.board_api.global.error.data.type.ErrorStatusType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class PolicyValidationAdvice {
    @ExceptionHandler(PolicyValidationException::class)
    fun handle(e: PolicyValidationException) =
        ErrorResponse(
            status = ErrorStatusType.POLICY_VIOLATION,
            message = e.getErrorMessage(),
            details = e.getErrorDetails()
        ).let { ResponseEntity.badRequest().body(it) }
}