package gg.dak.board_api.domain.account.util

import gg.dak.board_api.domain.account.config.LoginProperties
import gg.dak.board_api.domain.account.data.enitty.RefreshToken
import gg.dak.board_api.domain.account.data.event.LoginTokenCreateEvent
import gg.dak.board_api.domain.account.data.type.TokenType
import gg.dak.board_api.domain.account.repository.RefreshTokenRepository
import gg.dak.board_api.domain.account.repository.UuidTokenRepository
import gg.dak.board_api.domain.account.util.impl.LoginTokenGeneratorImpl
import gg.dak.board_api.global.account.util.UuidTokenGenerator
import gg.dak.board_api.test_utils.TestUtil
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import org.springframework.context.ApplicationEventPublisher
import java.util.*
import kotlin.random.Random

class LoginTokenGeneratorTest {
    private lateinit var loginProperties: LoginProperties
    private lateinit var jwtTokenGenerator: JwtTokenGenerator
    private lateinit var uuidTokenGenerator: UuidTokenGenerator
    private lateinit var applicationEventPublisher: ApplicationEventPublisher
    private lateinit var target: LoginTokenGenerator
    private lateinit var uuidTokenRepository: UuidTokenRepository
    private lateinit var refreshTokenRepository: RefreshTokenRepository

    @BeforeEach
    fun setUp() {
        loginProperties = mock()
        jwtTokenGenerator = mock()
        uuidTokenGenerator = mock()
        applicationEventPublisher = mock()
        uuidTokenRepository = mock()
        refreshTokenRepository = mock()
        target = LoginTokenGeneratorImpl(loginProperties, jwtTokenGenerator, uuidTokenGenerator, applicationEventPublisher, uuidTokenRepository, refreshTokenRepository)
    }

    /* LoginTokenGenerator - 로그인 토큰 발급 성공테스트
    LoginTokenGenerator.generate(?: String)
    인자로 받은 id를 통해 로그인토큰을 발급한다
    - loginProperties에서 토큰 생성에 필요한 정보를 불러온다.
    - jwtTokenGenerator를 통해 accessToken을 발급한다.
    - uuidTokenGenerator를 통해 refreshToken을 발급한다.
     */
    @Test @DisplayName("LoginTokenGenerator - 로그인 토큰 생성 성공테스트A")
    fun testGenerateLoginToken_positiveA() { //기존에 재발급토큰이 존재하지 않았을 경우
        //given
        val id = TestUtil.data().account().id()
        val refreshTokenExpireSecond = Random.nextLong()
        val accessTokenExpireSecond = Random.nextLong()
        val accessToken = TestUtil.data().account().token()
        val refreshToken = TestUtil.data().account().token()
        val optional = Optional.empty<RefreshToken>()

        //when
        whenever(loginProperties.refreshTokenProperties).thenReturn(mock())
        whenever(loginProperties.accessTokenProperties).thenReturn(mock())
        whenever(loginProperties.accessTokenProperties.expireSecond).thenReturn(accessTokenExpireSecond)
        whenever(loginProperties.refreshTokenProperties.expireSecond).thenReturn(refreshTokenExpireSecond)
        whenever(refreshTokenRepository.findById(id)).thenReturn(optional)
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
        verify(applicationEventPublisher, times(1)).publishEvent(LoginTokenCreateEvent(id, accessToken, refreshToken))
        verify(uuidTokenRepository, times(0)).deleteById(any())
    }

    @Test @DisplayName("LoginTokenGenerator - 로그인 토큰 생성 성공테스트B")
    fun testGenerateLoginToken_positiveB() { //기존에 재발급토큰이 존재하였을 경우
        //given
        val id = TestUtil.data().account().id()
        val refreshTokenExpireSecond = Random.nextLong()
        val accessTokenExpireSecond = Random.nextLong()
        val accessToken = TestUtil.data().account().token()
        val legacyRefreshToken = TestUtil.data().account().token()
        val refreshToken = TestUtil.data().account().token()
        val refreshTokenEntity = RefreshToken(id, legacyRefreshToken, -1)
        val optional = Optional.of(refreshTokenEntity)

        //when
        whenever(loginProperties.refreshTokenProperties).thenReturn(mock())
        whenever(loginProperties.accessTokenProperties).thenReturn(mock())
        whenever(loginProperties.accessTokenProperties.expireSecond).thenReturn(accessTokenExpireSecond)
        whenever(loginProperties.refreshTokenProperties.expireSecond).thenReturn(refreshTokenExpireSecond)
        whenever(refreshTokenRepository.findById(id)).thenReturn(optional)
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
        verify(applicationEventPublisher, times(1)).publishEvent(LoginTokenCreateEvent(id, accessToken, refreshToken))
        verify(uuidTokenRepository, times(1)).deleteById(legacyRefreshToken)
    }
}