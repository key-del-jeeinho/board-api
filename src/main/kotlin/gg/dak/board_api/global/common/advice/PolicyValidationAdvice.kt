package gg.dak.board_api.global.common.advice

import gg.dak.board_api.global.common.exception.PolicyValidationException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class PolicyValidationAdvice {
    @ExceptionHandler(PolicyValidationException::class) //TODO 나중에 ErrorResponse 관련 스팩 만들고 적용하기
    fun handle(e: PolicyValidationException) = ResponseEntity.badRequest().body("정책을 위반했습니다! - ${e.message}")
}