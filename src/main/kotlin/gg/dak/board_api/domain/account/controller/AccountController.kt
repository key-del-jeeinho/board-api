package gg.dak.board_api.domain.account.controller

import gg.dak.board_api.domain.account.data.request.LoginRequest
import gg.dak.board_api.domain.account.data.request.RefreshLoginRequest
import gg.dak.board_api.domain.account.data.request.RegisterRequest
import gg.dak.board_api.domain.account.data.response.LoginResponse
import gg.dak.board_api.domain.account.data.response.RegisterResponse
import gg.dak.board_api.domain.account.service.AccountService
import gg.dak.board_api.domain.account.service.RefreshLoginService
import gg.dak.board_api.domain.account.util.AccountConverter
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Api(tags = ["계정 API"])
@RestController
@RequestMapping("/api/v1/account")
class AccountController(
    private val accountConverter: AccountConverter,
    private val accountService: AccountService,
    private val refreshLoginService: RefreshLoginService
) {
    @ApiOperation(value = "회원가입", notes = "회원 초기정보를 통해 회원가입을 진행합니다.")
    @PostMapping("/register") //회원가입 트랜잭션을 수행합니다.
    fun register(@RequestBody request: RegisterRequest): ResponseEntity<RegisterResponse> =
        accountConverter.toDto(request) //요청정보를 통해 Dto를 구성합니다.
            .let { accountService.register(it) } //Dto를 통해 회원가입 로직을 수행하고, 가입된 계정정보를 반환합니다.
            .let { RegisterResponse(it.idx) } //응답객체를 구성하여 반환합니다.
            .let { ResponseEntity.ok(it) }

    @ApiOperation(value = "로그인", notes = "회원 인증정보(아이디, 비밀번호)를 통해 로그인토큰을 발급합니다.")
    @PostMapping("/login") //로그인 트랜잭션을 수행합니다.
    fun login(@RequestBody request: LoginRequest): ResponseEntity<LoginResponse> =
        accountConverter.toDto(request) //요청정보를 통해 Dto를 구성합니다.
            .let { accountService.login(it) } //Dto를 통해 로그인 로직을 수행하고, 로그인 토큰을 반환합니다.
            .let { accountConverter.toResponse(it) } //응답객체를 구성하여 반환합니다.
            .let { ResponseEntity.ok(it) }


    @ApiOperation(value = "로그인 연장", notes = "회원 인증 연장정보(재발급 토큰)를 통해 로그인토큰을 발급합니다..")
    @PostMapping("/login/refresh") //로그인 연장 트랜잭션을 수행합니다.
    fun refreshLogin(@RequestBody request: RefreshLoginRequest): ResponseEntity<LoginResponse> =
        refreshLoginService.refreshLogin(request.refreshToken) //로그인 연장로직을 수행하고, 로그인 토큰을 반환합니다.
            .let { accountConverter.toResponse(it) } //응답객체를 구성하여 반환합니다.
            .let { ResponseEntity.ok(it) }
}