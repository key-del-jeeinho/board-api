package gg.dak.board_api.domain.account.service

import gg.dak.board_api.TestDummyDataUtil
import gg.dak.board_api.domain.account.data.dto.LoginTokenDto
import gg.dak.board_api.domain.account.data.type.TokenType
import gg.dak.board_api.domain.account.util.LoginTokenGenerator
import gg.dak.board_api.domain.account.util.UuidTokenGenerator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class RefreshLoginServiceTest {
    private lateinit var uuidTokenGenerator: UuidTokenGenerator
    private lateinit var loginTokenGenerator: LoginTokenGenerator
    private lateinit var target: RefreshLoginService

    @BeforeEach
    fun setUp() {
        uuidTokenGenerator = mock()
        loginTokenGenerator = mock()
        target = RefreshLoginServiceImpl(uuidTokenGenerator, loginTokenGenerator)
    }

    /* RefreshLoginService - 로그인 연장 성공테스트
    RefreshLoginService.refreshLogin(?: String)
    재발급 토큰을 통해, 계정의 id를 추출하고, 로그인토큰을 생성하여 반환한다.
    계정 id추출로직은 RefreshTokenRepository와 UuidTokenGenerator를 통해 구현한다.
    로그인토큰 생성로직은 LoginTokenGenerator를 통해 구현한다.
     */
    @Test @DisplayName("RefreshLoginService - 로그인 연장 성공테스트")
    fun testRefreshLogin_positive() {
        //given
        val id = TestDummyDataUtil.id()
        val refreshToken = TestDummyDataUtil.token()
        val loginTokenDto = mock<LoginTokenDto>()

        //when
        whenever(uuidTokenGenerator.decode(refreshToken)).thenReturn(mapOf(
            "id" to id,
            "type" to TokenType.LOGIN_REFRESH.key,
            "expiration" to false.toString()
        ))
        whenever(loginTokenGenerator.generate(id)).thenReturn(loginTokenDto)

        //then
        val result = target.refreshLogin(refreshToken)

        assertEquals(result, loginTokenDto)
    }
}