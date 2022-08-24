package gg.dak.board_api.domain.account.controller

import gg.dak.board_api.domain.account.data.request.RegisterRequest
import gg.dak.board_api.domain.account.data.response.RegisterResponse
import gg.dak.board_api.domain.account.service.AccountService
import gg.dak.board_api.domain.account.util.AccountConverter
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/account")
class AccountController(
    private val accountConverter: AccountConverter,
    private val accountService: AccountService
) {
    @PostMapping("/register") //회원가입 트랜잭션을 수행합니다.
    fun register(@RequestBody request: RegisterRequest): ResponseEntity<RegisterResponse>  =
        accountConverter.toDto(request) //요청정보를 통해 Dto를 구성합니다.
            .let { accountService.register(it) } //Dto를 통해 회원가입 로직을 수행하고, 가입된 계정정보를 반환합니다.
            .let { RegisterResponse(it.idx) } //응답객체를 구성하여 반환합니다.
            .let { ResponseEntity.ok(it) }
}