package gg.dak.board_api.domain.account.service

import gg.dak.board_api.TestDummyDataUtil
import gg.dak.board_api.domain.account.data.dto.AccountDto
import gg.dak.board_api.domain.account.data.enitty.Account
import gg.dak.board_api.domain.account.util.AccountConverter
import gg.dak.board_api.global.account.repository.AccountRepository
import org.junit.jupiter.api.Assertions.assertTrue
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
    fun testFindAllAccount() {
        //given
        val page = Random.nextInt().absoluteValue
        val size = (1..100).random()
        val pagination = PageRequest.of(page, size)
        val accounts = (1..size).map { TestDummyDataUtil.account() }
        val data = PageImpl(accounts)
        val dto = mock<AccountDto>()

        //when
        whenever(accountRepository.findBy(pagination)).thenReturn(data)
        whenever(accountConverter.toDto(any<Account>())).thenReturn(dto)

        //then
        val result = target.findAllAccount(pagination)
        assertTrue(result.content.stream().allMatch{ it == dto })
    }
}