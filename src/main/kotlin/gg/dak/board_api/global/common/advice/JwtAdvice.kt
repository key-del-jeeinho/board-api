package gg.dak.board_api.global.common.advice

import gg.dak.board_api.global.account.exception.JwtException
import gg.dak.board_api.global.error.data.response.ErrorResponse
import gg.dak.board_api.global.error.data.type.ErrorStatusType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class JwtAdvice {
    @ExceptionHandler(JwtException::class)
    fun handle(e: JwtException) =
        ErrorResponse(
            status = ErrorStatusType.JWT_ERROR,
            message = e.getErrorMessage(),
            details = e.getErrorDetails()
        ).let { ResponseEntity.badRequest().body(it) }
}