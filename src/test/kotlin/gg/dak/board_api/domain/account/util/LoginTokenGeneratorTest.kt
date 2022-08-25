package gg.dak.board_api.domain.account.util

import gg.dak.board_api.TestDummyDataUtil
import gg.dak.board_api.domain.account.config.LoginProperties
import gg.dak.board_api.domain.account.data.type.TokenType
import gg.dak.board_api.domain.account.util.impl.LoginTokenGeneratorImpl
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.random.Random

class LoginTokenGeneratorTest {
    private lateinit var loginProperties: LoginProperties
    private lateinit var jwtTokenGenerator: JwtTokenGenerator
    private lateinit var uuidTokenGenerator: UuidTokenGenerator
    private lateinit var target: LoginTokenGenerator

    @BeforeEach
    fun setUp() {
        loginProperties = mock()
        jwtTokenGenerator = mock()
        uuidTokenGenerator = mock()
        target = LoginTokenGeneratorImpl(loginProperties, jwtTokenGenerator, uuidTokenGenerator)
    }

    /* LoginTokenGenerator - 로그인 토큰 발급 성공테스트
    LoginTokenGenerator.generate(?: String)
    인자로 받은 id를 통해 로그인토큰을 발급한다
    - loginProperties에서 토큰 생성에 필요한 정보를 불러온다.
    - jwtTokenGenerator를 통해 accessToken을 발급한다.
    - uuidTokenGenerator를 통해 refreshToken을 발급한다.
     */
    @Test @DisplayName("LoginTokenGenerator - 로그인 토큰 생성 성공테스트")
    fun testGenerateLoginToken_positive() {
        //given
        val id = TestDummyDataUtil.id()
        val refreshTokenExpireSecond = Random.nextLong()
        val accessTokenExpireSecond = Random.nextLong()
        val accessToken = TestDummyDataUtil.token()
        val refreshToken = TestDummyDataUtil.token()

        //when
        whenever(loginProperties.refreshTokenProperties).thenReturn(mock())
        whenever(loginProperties.accessTokenProperties).thenReturn(mock())
        whenever(loginProperties.accessTokenProperties.expireSecond).thenReturn(accessTokenExpireSecond)
        whenever(loginProperties.refreshTokenProperties.expireSecond).thenReturn(refreshTokenExpireSecond)
        whenever(jwtTokenGenerator.generate(mapOf("id" to id,"type" to TokenType.LOGIN_ACCESS.key), accessTokenExpireSecond)).thenReturn(accessToken)
        whenever(uuidTokenGenerator.generate(mapOf(
            "id" to id,
            "type" to TokenType.LOGIN_REFRESH.key,
            "expiration" to false.toString()
        ), refreshTokenExpireSecond)).thenReturn(refreshToken)

        //then
        val result = target.generate(id)

        assertEquals(result.accessToken, accessToken)
        assertEquals(result.refreshToken, refreshToken)
    }
}