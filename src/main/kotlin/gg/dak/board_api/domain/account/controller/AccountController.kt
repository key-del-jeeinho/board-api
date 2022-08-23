package gg.dak.board_api.domain.account.controller

import gg.dak.board_api.domain.account.data.request.RegisterRequest
import gg.dak.board_api.domain.account.data.response.RegisterResponse
import gg.dak.board_api.domain.account.service.AccountService
import gg.dak.board_api.domain.account.util.AccountConverter
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/account")
class AccountController(
    private val accountConverter: AccountConverter,
    private val accountService: AccountService
) {
    @PostMapping("/register")
    fun register(request: RegisterRequest): ResponseEntity<RegisterResponse>  =
        accountConverter.toDto(request)
            .let { accountService.register(it) }
            .let { RegisterResponse(it.idx) }
            .let { ResponseEntity.ok(it) }
}