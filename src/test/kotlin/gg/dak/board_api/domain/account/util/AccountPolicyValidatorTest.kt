package gg.dak.board_api.domain.account.util

import gg.dak.board_api.TestDummyDataUtil
import gg.dak.board_api.global.account.data.dto.AccountDto
import gg.dak.board_api.domain.account.data.enitty.Account
import gg.dak.board_api.domain.account.data.type.OperationType
import gg.dak.board_api.global.account.repository.AccountRepository
import gg.dak.board_api.domain.account.util.impl.AccountPolicyValidatorImpl
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.springframework.security.crypto.password.PasswordEncoder
import java.util.Optional

class AccountPolicyValidatorTest {
    private lateinit var accountRepository: AccountRepository
    private lateinit var passwordEncoder: PasswordEncoder
    private lateinit var target: AccountPolicyValidator

    @BeforeEach
    fun setUp() {
        accountRepository = mock()
        passwordEncoder = mock()
        target = AccountPolicyValidatorImpl(accountRepository, passwordEncoder)
    }

    @Test @DisplayName("AccountPolicyValidator - 회원가입 정책 검사 성공테스트")
    fun testRegisterPolicyValidate_success() {
        //given
        val nickname = TestDummyDataUtil.nickname(length = (2..5).random())
        val dto = mock<AccountDto>()

        //when
        whenever(dto.nickname).thenReturn(nickname)

        //then
        assertDoesNotThrow { target.validate(OperationType.REGISTER, dto) }
    }

    /* AccountPolicyValidator - 로그인 정책 검사 성공테스트
    AccountPolicyValidator.validate(OperationType.LOGIN, ?: AccountDto)
    Dto에서 인증정보를 추출하여, 로그인 정책 검사 로직을 수행한다.
    로그인 정책은 다음과 같다.
    - 인증정보의 id를 소유한 계정이 존재할 것
    - 인증정보의 password가 id를 소유한 계정의 암호와 동일할 것
     */
    @Test @DisplayName("AccountPolicyValidator - 로그인 정책 검사 성공테스트")
    fun testLoginPolicyValidate_success() {
        //given
        val id = TestDummyDataUtil.id()
        val password = TestDummyDataUtil.password()
        val encodedPassword = TestDummyDataUtil.encodedPassword()
        val dto = mock<AccountDto>()
        val optional = mock<Optional<Account>>()
        val entity = mock<Account>()

        //when
        whenever(dto.id).thenReturn(id)
        whenever(dto.password).thenReturn(password)
        whenever(accountRepository.findById(id)).thenReturn(optional)
        whenever(optional.isPresent).thenReturn(true)
        whenever(optional.get()).thenReturn(entity)
        whenever(entity.encodedPassword).thenReturn(encodedPassword)
        whenever(passwordEncoder.matches(password, encodedPassword)).thenReturn(true)

        //then
        assertDoesNotThrow { target.validate(OperationType.LOGIN, dto) }
    }
}