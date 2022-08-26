package gg.dak.board_api.domain.account.controller

import gg.dak.board_api.TestDummyDataUtil
import gg.dak.board_api.domain.account.data.dto.AccountDto
import gg.dak.board_api.domain.account.data.response.AccountQueryResponse
import gg.dak.board_api.domain.account.data.response.PageableAccountQueryResponse
import gg.dak.board_api.domain.account.service.AccountQueryService
import gg.dak.board_api.domain.account.util.AccountQueryConverter
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import kotlin.math.absoluteValue
import kotlin.random.Random

class AccountQueryControllerTest {
    private lateinit var accountQueryService: AccountQueryService
    private lateinit var accountQueryConverter: AccountQueryConverter
    private lateinit var target: AccountQueryController

    @BeforeEach
    fun setUp() {
        accountQueryService = mock()
        accountQueryConverter = mock()
        target = AccountQueryController(accountQueryService, accountQueryConverter)
    }

    /* AccountQueryController - 전체 계정목록 조회 성공테스트
    AccountQueryController.findAllAccountWithPagination(?: Int, ?: Int)
    요청에 있는 페이지네이션 정보를 통해, 페이지네이션된 계정목록을 조회하고, 반환한다.
    페이지네이션 계정목록의 조회는 AccountQueryService에게 위임한다.
    */
    @Test @DisplayName("AccountQueryController - 전체 계정목록 조회 성공테스트")
    fun testFindAllAccountWithPagination_positive() {
        //given
        val page = Random.nextInt().absoluteValue
        val size = (0..100).random()
        val pagination = PageRequest.of(page, size)
        val accounts = (1..size).map { TestDummyDataUtil.accountDto(isPasswordEncoded = true) }
        val data = PageImpl(accounts)
        val response = mock<AccountQueryResponse>()
        val pageableResponse = mock<PageableAccountQueryResponse>()

        //when
        whenever(accountQueryService.findAllAccount(pagination)).thenReturn(data)
        whenever(accountQueryConverter.toResponse(any())).thenReturn(response)
        whenever(accountQueryConverter.toPageabelResponse(any())).thenReturn(pageableResponse)

        //then
        val result = target.findAllAccountWithPagination(page = page, size = size)
        assertTrue(result.statusCode.is2xxSuccessful)
        assertNotNull(result.body)
        assertEquals(result.body, pageableResponse)
    }

    /* AccountQueryController - 인덱스로 계정 조회 성공테스트
    AccountQueryController.findAccountByIndex(?: Long)
    요청에 있는 인덱스를 통해, 계정을 조회한다.
    계정 조회로직은 AccountQueryService에게 위임한다.
     */
    @Test @DisplayName("AccountQueryController - 인덱스로 계정 조회 성공테스트")
    fun testFindAccountByIndex_positive() {
        //given
        val idx = Random.nextLong()
        val dto = mock<AccountDto>()
        val response = mock<AccountQueryResponse>()

        //when
        whenever(accountQueryService.findAccountByIndex(idx)).thenReturn(dto)
        whenever(accountQueryConverter.toResponse(dto)).thenReturn(response)

        //then
        val result = target.findAccountByIndex(idx)
        assertTrue(result.statusCode.is2xxSuccessful)
        assertNotNull(result.body)
        assertEquals(result.body, response)
    }

    /* AccountQueryController - ID로 계정조회 성공테스트
    AccountQueryController.findAccountById(?: String)
    요청에 있는 ID를 통해, 계정을 조회한다.
    계정 조회로직은 AccountQueryService에게 위임한다.
     */
    @Test @DisplayName("AccountQueryController - ID로 계정조회 성공테스트")
    fun testFindAccountById_positive() {
        //given
        val id = TestDummyDataUtil.id()
        val dto = mock<AccountDto>()
        val response = mock<AccountQueryResponse>()

        //when
        whenever(accountQueryService.findAccountById(id)).thenReturn(dto)
        whenever(accountQueryConverter.toResponse(dto)).thenReturn(response)

        //then
        val result = target.findAccountById(id)
        assertTrue(result.statusCode.is2xxSuccessful)
        assertNotNull(result.body)
        assertEquals(result.body, response)
    }
}