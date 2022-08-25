package gg.dak.board_api.domain.account.util

import gg.dak.board_api.TestDummyDataUtil
import gg.dak.board_api.domain.account.data.dto.AccountDto
import gg.dak.board_api.domain.account.data.type.OperationType
import gg.dak.board_api.domain.account.util.impl.AccountProcessorImpl
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
import kotlin.random.Random

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
        val id = TestDummyDataUtil.id()
        val password = TestDummyDataUtil.password()
        val nickname = TestDummyDataUtil.nickname((2..5).random())
        val dto = AccountDto(
            idx = Random.nextLong(1, Long.MAX_VALUE),
            nickname = nickname,
            id = id,
            password = password,
            isPasswordEncoded = false
        )
        val encodedPassword = TestDummyDataUtil.encodedPassword()

        //when
        whenever(passwordEncoder.encode(password)).thenReturn(encodedPassword)

        //then
        val result = target.process(OperationType.REGISTER, dto)

        assertEquals(result.idx, 0)
        assertTrue(result.isPasswordEncoded)
        assertEquals(result.password, encodedPassword)
        verify(passwordEncoder, times(1)).encode(password)
    }
}