package gg.dak.board_api.domain.account.controller

import gg.dak.board_api.domain.account.data.dto.AccountDto
import gg.dak.board_api.domain.account.data.request.RegisterRequest
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

    @Test @DisplayName("회원가입 성공테스트")
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
}