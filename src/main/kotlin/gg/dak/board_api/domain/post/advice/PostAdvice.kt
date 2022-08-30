package gg.dak.board_api.domain.post.advice

import gg.dak.board_api.domain.post.controller.PostController
import gg.dak.board_api.domain.post.exception.UnknownPostException
import gg.dak.board_api.global.error.data.response.ErrorResponse
import gg.dak.board_api.global.error.data.type.ErrorStatusType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice(basePackageClasses = [PostController::class])
class PostAdvice {
    @ExceptionHandler(UnknownPostException::class)
    fun handle(e: UnknownPostException) =
        ErrorResponse(
            status = ErrorStatusType.UNKNOWN_POST,
            message = e.getErrorMessage(),
            details = e.getErrorDetails()
        ).let { ResponseEntity.badRequest().body(it) }
}