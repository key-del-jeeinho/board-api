package gg.dak.board_api.domain.account.listener

import gg.dak.board_api.TestDummyDataUtil
import gg.dak.board_api.domain.account.config.LoginProperties
import gg.dak.board_api.domain.account.data.enitty.RefreshToken
import gg.dak.board_api.domain.account.data.event.LoginTokenCreateEvent
import gg.dak.board_api.domain.account.repository.RefreshTokenRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.random.Random

class RefreshTokenCacheListenerTest {
    private lateinit var refreshTokenRepository: RefreshTokenRepository
    private lateinit var loginProperties: LoginProperties
    private lateinit var target: RefreshTokenCacheListener

    @BeforeEach
    fun setUp() {
        refreshTokenRepository = mock()
        loginProperties = mock()
        target = RefreshTokenCacheListener(refreshTokenRepository, loginProperties)
    }

    /* RefreshTokenCacheListener - 재발급 토큰 캐싱 성공테스트
    RefreshTokenCacheListener.handle(?: LoginTokenCreateEvent)
    Event에서 재발급토큰과 ID를 추출하고, 이를 캐싱한다.
    캐싱은 RefreshTokenRepository를 통해 수행한다.
    이때 캐시의 유효기간은 RefreshToken의 유효기간과 같도록 한다. (실제 실행시간으로 인한 1초 이내의 차이는 무시한다.)
     */
    @Test @DisplayName("RefreshTokenCacheListener - 재발급 토큰 캐싱 성공테스트")
    fun testCachingRefreshToken_positive() {
        //given
        val id = TestDummyDataUtil.id()
        val accessToken = TestDummyDataUtil.token()
        val refreshToken = TestDummyDataUtil.token()
        val ttl = Random.nextLong()
        val event = LoginTokenCreateEvent(id, accessToken = accessToken, refreshToken = refreshToken)
        val entity = RefreshToken(id = id, token = refreshToken, timeToLive = ttl)

        //when
        whenever(loginProperties.refreshTokenProperties).thenReturn(mock())
        whenever(loginProperties.refreshTokenProperties.expireSecond).thenReturn(ttl)

        //then
        target.handle(event)
        verify(refreshTokenRepository, times(1)).save(entity)
    }
}