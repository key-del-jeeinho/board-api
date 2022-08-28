package gg.dak.board_api.domain.account.advice

import gg.dak.board_api.domain.account.controller.AccountController
import gg.dak.board_api.domain.account.exception.UnknownUuidTokenException
import gg.dak.board_api.global.account.exception.UnknownAccountException
import gg.dak.board_api.global.error.data.response.ErrorResponse
import gg.dak.board_api.global.error.data.type.ErrorStatusType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice(basePackageClasses = [AccountController::class])
class AccountAdvice {
    @ExceptionHandler(UnknownUuidTokenException::class)
    fun handle(e: UnknownUuidTokenException) =
        ErrorResponse(
            status = ErrorStatusType.UNKNOWN_TOKEN,
            message = e.getErrorMessage(),
            details = e.getErrorDetails()
        ).let { ResponseEntity.badRequest().body(it) }

    @ExceptionHandler(UnknownAccountException::class)
    fun handle(e: UnknownAccountException) =
        ErrorResponse(
            status = ErrorStatusType.UNKNOWN_ACCOUNT,
            message = e.getErrorMessage(),
            details = e.getErrorDetails()
        ).let { ResponseEntity.badRequest().body(it) }
}