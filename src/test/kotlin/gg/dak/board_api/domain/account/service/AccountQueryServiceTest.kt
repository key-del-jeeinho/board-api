package gg.dak.board_api.domain.account.service

import gg.dak.board_api.domain.account.data.enitty.Account
import gg.dak.board_api.domain.account.util.AccountConverter
import gg.dak.board_api.global.account.data.dto.AccountDto
import gg.dak.board_api.global.account.repository.AccountRepository
import gg.dak.board_api.test_utils.TestUtil
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import java.util.*
import kotlin.math.absoluteValue
import kotlin.random.Random

class AccountQueryServiceTest {
    private lateinit var accountRepository: AccountRepository
    private lateinit var accountConverter: AccountConverter
    private lateinit var target: AccountQueryService

    @BeforeEach
    fun setUp() {
        accountRepository = mock()
        accountConverter = mock()
        target = AccountQueryServiceImpl(accountRepository, accountConverter)
    }

    /* AccountQueryService - 전체 계정목록 조회 성공테스트
    AccountQueryService.findAllAccount(?: PageRequest)
    인자로 받은 PageRequest를 통해 페이지네이션된 계정목록을 조회한다.
     */
    @Test @DisplayName("AccountQueryService - 전체 계정목록 조회 성공테스트")
    fun testFindAllAccount_positive() {
        //given
        val page = Random.nextInt().absoluteValue
        val size = (1..100).random()
        val pagination = PageRequest.of(page, size)
        val accounts = (1..size).map { TestUtil.data().account().entity() }
        val data = PageImpl(accounts)
        val dto = mock<AccountDto>()

        //when
        whenever(accountRepository.findBy(pagination)).thenReturn(data)
        whenever(accountConverter.toDto(any<Account>())).thenReturn(dto)

        //then
        val result = target.findAllAccount(pagination)
        assertTrue(result.content.stream().allMatch{ it == dto })
    }

    /* AccountQueryService - 인덱스로 계정 조회 성공테스트
    AccountQueryService.findAccountByIndex(?: Long)
    인자로 받은 인덱스를 통해 계정을 조회한다.
     */
    @Test @DisplayName("AccountQueryService - 인덱스로 계정 조회 성공테스트")
    fun testFindAccountByIndex_positive() {
        //given
        val idx = Random.nextLong()
        val entity = mock<Account>()
        val optional = Optional.of(entity)
        val dto = mock<AccountDto>()

        //when
        whenever(accountRepository.findById(idx)).thenReturn(optional)
        whenever(accountConverter.toDto(entity)).thenReturn(dto)

        //then
        val result = target.findAccountByIndex(idx)
        assertEquals(result, dto)
    }

    /* AccountQueryService - ID로 계정조회 성공테스트
    AccountQueryService.findAccountById(?: String)
    인자로 받은 ID를 통해 계정을 조회한다.
     */
    @Test @DisplayName("AccountQueryService - ID로 계정 조회 성공테스트")
    fun testFindAccountById_positive() {
        //given
        val id = TestUtil.data().account().id()
        val entity = mock<Account>()
        val optional = Optional.of(entity)
        val dto = mock<AccountDto>()

        //when
        whenever(accountRepository.findById(id)).thenReturn(optional)
        whenever(accountConverter.toDto(entity)).thenReturn(dto)

        //then
        val result = target.findAccountById(id)
        assertEquals(result, dto)
    }
}