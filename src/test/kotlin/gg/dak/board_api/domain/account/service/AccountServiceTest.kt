package gg.dak.board_api.domain.account.service

import gg.dak.board_api.domain.account.data.dto.AccountDto
import gg.dak.board_api.domain.account.data.enitty.Account
import gg.dak.board_api.domain.account.data.type.OperationType
import gg.dak.board_api.domain.account.repository.AccountRepository
import gg.dak.board_api.domain.account.util.AccountConverter
import gg.dak.board_api.domain.account.util.AccountPolicyValidator
import gg.dak.board_api.domain.account.util.AccountProcessor
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class AccountServiceTest {

    private lateinit var accountPolicyValidator: AccountPolicyValidator
    private lateinit var accountProcessor: AccountProcessor
    private lateinit var accountConverter: AccountConverter
    private lateinit var accountRepository: AccountRepository
    private lateinit var target: AccountService

    @BeforeEach
    fun setUp() {
        accountPolicyValidator = mock()
        accountProcessor = mock()
        accountConverter = mock()
        accountRepository = mock()
        target = AccountServiceImpl(accountPolicyValidator, accountProcessor, accountConverter, accountRepository)
    }

    @Test @DisplayName("AccountService - 회원가입 성공테스트")
    fun testRegister_positive() {
        //given
        val dto = mock<AccountDto>()
        val processedDto = mock<AccountDto>()
        val entity = mock<Account>()
        val savedEntity = mock<Account>()
        val registeredDto = mock<AccountDto>()

        //when
        whenever(accountProcessor.process(OperationType.REGISTER, dto)).thenReturn(processedDto)
        whenever(accountConverter.toEntity(processedDto)).thenReturn(entity)
        whenever(accountRepository.save(entity)).thenReturn(savedEntity)
        whenever(accountConverter.toDto(savedEntity)).thenReturn(registeredDto)

        //test
        val result = target.register(dto)

        assertEquals(result, registeredDto)
        verify(accountRepository, times(1)).save(entity)
        verify(accountPolicyValidator, times(1)).validate(OperationType.REGISTER, dto)
    }
}