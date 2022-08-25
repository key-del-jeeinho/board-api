package gg.dak.board_api.domain.account.service

import gg.dak.board_api.TestDummyDataUtil
import gg.dak.board_api.domain.account.data.dto.AccountDto
import gg.dak.board_api.domain.account.data.dto.LoginTokenDto
import gg.dak.board_api.domain.account.data.enitty.Account
import gg.dak.board_api.domain.account.data.type.OperationType
import gg.dak.board_api.global.account.repository.AccountRepository
import gg.dak.board_api.domain.account.util.AccountConverter
import gg.dak.board_api.domain.account.util.AccountPolicyValidator
import gg.dak.board_api.domain.account.util.AccountProcessor
import gg.dak.board_api.domain.account.util.LoginTokenGenerator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.context.ApplicationEventPublisher

class AccountServiceTest {

    private lateinit var accountPolicyValidator: AccountPolicyValidator
    private lateinit var accountProcessor: AccountProcessor
    private lateinit var accountConverter: AccountConverter
    private lateinit var accountRepository: AccountRepository
    private lateinit var loginTokenGenerator: LoginTokenGenerator
    private lateinit var applicationEventPublisher: ApplicationEventPublisher
    private lateinit var target: AccountService

    @BeforeEach
    fun setUp() {
        accountPolicyValidator = mock()
        accountProcessor = mock()
        accountConverter = mock()
        accountRepository = mock()
        loginTokenGenerator = mock()
        applicationEventPublisher = mock()
        target = AccountServiceImpl(accountPolicyValidator, accountProcessor, accountConverter, accountRepository, loginTokenGenerator)
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

        //then
        val result = target.register(dto)

        assertEquals(result, registeredDto)
        verify(accountRepository, times(1)).save(entity)
        verify(accountPolicyValidator, times(1)).validate(OperationType.REGISTER, dto)
    }

    /* AccountService - 로그인 성공테스트
    AccountService.login(?: AccountDto)
    Dto에서 인증정보를 추출해, 인증정보를 검증하고, 로그인 토큰을 생성하여 반환한다.
    인증정보의 검증은 AccountValidator에서 이루어진다.
    - 모든 토큰은 만료기한을 가지고있다. 만료기한은 AccountProperties에서 가져온다
    - accessToken은 사용자의 id를 담고있는 JWT토큰이어야한다.
    - refreshToken은 어떠한 값도 담지 않은 UUID의 형식이어야한다.
    - refreshToken은 캐시 저장소에 만료기한까지 존재해야한다. 이 기능은 토큰 생성시 사용되는 util layer로 위임한다.
    토큰의 실제 생성 로직은 util layer로 위임한다.

    로그인 성공시, 로그인 토큰 생성 이벤트를 발행해야한다.
     */
    @Test @DisplayName("AccountService - 로그인 성공테스트")
    fun testLogin_positive() {
        //given
        val dto = mock<AccountDto>()
        val id = TestDummyDataUtil.id()
        val password = TestDummyDataUtil.password()
        val accessToken = TestDummyDataUtil.token()
        val refreshToken = TestDummyDataUtil.token()
        val loginTokenDto = LoginTokenDto(accessToken, refreshToken)

        //when
        whenever(dto.id).thenReturn(id)
        whenever(dto.password).thenReturn(password)
        whenever(loginTokenGenerator.generate(id)).thenReturn(loginTokenDto)

        //then
        val result = target.login(dto)

        verify(accountPolicyValidator, times(1)).validate(OperationType.LOGIN, dto)
        assertEquals(result, loginTokenDto)
    }
}