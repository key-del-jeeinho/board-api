package gg.dak.board_api.domain.account.util

import gg.dak.board_api.TestDummyDataUtil
import gg.dak.board_api.domain.account.data.dto.AccountDto
import gg.dak.board_api.domain.account.data.type.OperationType
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class AccountPolicyValidatorTest {
    private lateinit var target: AccountPolicyValidator

    @BeforeEach
    fun setUp() {
        target = AccountPolicyValidatorImpl()
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
}