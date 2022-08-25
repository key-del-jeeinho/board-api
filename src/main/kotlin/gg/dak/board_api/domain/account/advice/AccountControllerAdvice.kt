package gg.dak.board_api.domain.account.advice

import gg.dak.board_api.domain.account.controller.AccountController
import gg.dak.board_api.domain.account.exception.UnknownUuidTokenException
import gg.dak.board_api.global.error.ErrorResponse
import gg.dak.board_api.global.error.ErrorStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice(basePackageClasses = [AccountController::class])
class AccountControllerAdvice {
    @ExceptionHandler(UnknownUuidTokenException::class)
    fun handle(e: UnknownUuidTokenException) =
        ErrorResponse(
            status = ErrorStatus.UNKNOWN_TOKEN,
            message = e.getErrorMessage(),
            details = e.getErrorDetails()
        ).let { ResponseEntity.badRequest().body(it) }
}