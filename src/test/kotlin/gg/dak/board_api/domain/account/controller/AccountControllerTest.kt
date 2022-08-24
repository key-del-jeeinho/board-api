package gg.dak.board_api.domain.account.controller

import gg.dak.board_api.domain.account.data.dto.LoginTokenDto
import gg.dak.board_api.domain.account.data.dto.AccountDto
import gg.dak.board_api.domain.account.data.request.LoginRequest
import gg.dak.board_api.domain.account.data.request.RegisterRequest
import gg.dak.board_api.domain.account.data.response.LoginResponse
import gg.dak.board_api.domain.account.service.AccountService
import gg.dak.board_api.domain.account.util.AccountConverter
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.random.Random

class AccountControllerTest {
    private lateinit var accountConverter: AccountConverter
    private lateinit var accountService: AccountService
    private lateinit var target: AccountController

    @BeforeEach
    fun setUp() {
        accountConverter = mock()
        accountService = mock()
        target = AccountController(accountConverter, accountService)
    }

    @Test @DisplayName("AccountController - 회원가입 성공테스트")
    fun testRegister_positive() {
        //given
        val request = mock<RegisterRequest>()
        val dto = mock<AccountDto>()
        val registeredDto = mock<AccountDto>()
        val idx = Random.nextLong()

        //when
        whenever(accountConverter.toDto(request)).thenReturn(dto)
        whenever(accountService.register(dto)).thenReturn(registeredDto)
        whenever(registeredDto.idx).thenReturn(idx)

        //then
        val result = target.register(request)

        assertTrue(result.statusCode.is2xxSuccessful)
        assertNotNull(result.body)
        assertEquals(result.body!!.accountId, idx)
    }

    /* AccountController - 로그인 성공테스트
    AccountController.login(?: Request)
    요청에서 인증정보를 추출해, 로그인 로직을 수행하고, 이에대한 결과를 반환한다.
    인증정보는 id, password가 있으며, 이를 AccountDto형태로 치환하여 AccountService에게 로그인 로직을 위임한다.
    이후, Service에서 반환된 결과를 Response로 변환하여 반환한다.
    로그인 성공시 반환값에는 Service에서 반환한 accessToken과 refreshToken이 포함되어있어야한다.
     */
    @Test @DisplayName("AccountController - 로그인 성공테스트")
    fun testLogin_positive() {
        //given
        val request = mock<LoginRequest>()
        val dto = mock<AccountDto>()
        val loginTokenDto = mock<LoginTokenDto>()
        val response = mock<LoginResponse>()

        //when
        whenever(accountConverter.toDto(request)).thenReturn(dto)
        whenever(accountService.login(dto)).thenReturn(loginTokenDto)
        whenever(accountConverter.toResponse(loginTokenDto)).thenReturn(response)

        //then
        val result = target.login(request)
        assertTrue(result.statusCode.is2xxSuccessful)
        assertNotNull(result.body)
        assertEquals(result.body!!, response)
    }
}