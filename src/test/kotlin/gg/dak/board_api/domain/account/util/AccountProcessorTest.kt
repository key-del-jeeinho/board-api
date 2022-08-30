package gg.dak.board_api.domain.account.util

import gg.dak.board_api.domain.account.data.type.OperationType
import gg.dak.board_api.domain.account.util.impl.AccountProcessorImpl
import gg.dak.board_api.test_utils.TestUtil
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.security.crypto.password.PasswordEncoder

class AccountProcessorTest {
    private lateinit var passwordEncoder: PasswordEncoder
    private lateinit var target: AccountProcessor

    @BeforeEach
    fun setUp() {
        passwordEncoder = mock()
        target = AccountProcessorImpl(passwordEncoder)
    }

    /* AccountProcessor - 회원가입 데이터 전처리 성공테스트
    AccountProcessor.process(OperationType.REGISTER, ?: AccountDto)
    Dto에 대해, 회원가입시 필요한 전처리 로직을 수행하고, 이에대한 결과를 반환한다.
    회원가입시에 필요한 전처리 로직은 다음과 같다
    - idx를 0으로 변환한다.
    - password를 인코딩한다. (PasswordEncoder 사용)
     */
    @Test @DisplayName("AccountProcessor - 회원가입 데이터 전처리 성공테스트")
    fun testRegisterDataProcess_positive() {
        //given
        val dto = TestUtil.data().account().dto(false)
        val encodedPassword = TestUtil.data().account().encodedPassword()

        //when
        whenever(passwordEncoder.encode(dto.password)).thenReturn(encodedPassword)

        //then
        val result = target.process(OperationType.REGISTER, dto)

        assertEquals(result.idx, 0)
        assertTrue(result.isPasswordEncoded)
        assertEquals(result.password, encodedPassword)
        verify(passwordEncoder, times(1)).encode(dto.password)
    }
}