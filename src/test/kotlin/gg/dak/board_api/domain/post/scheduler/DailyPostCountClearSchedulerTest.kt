package gg.dak.board_api.domain.post.scheduler

import gg.dak.board_api.domain.post.repository.DailyPostCountRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class DailyPostCountClearSchedulerTest {
    private lateinit var dailyPostCountRepository: DailyPostCountRepository
    private lateinit var dailyPostCountClearScheduler: DailyPostCountClearScheduler

    @BeforeEach
    fun setUp() {
        dailyPostCountRepository = mock()
        dailyPostCountClearScheduler = DailyPostCountClearScheduler(dailyPostCountRepository)
    }

    @Test @DisplayName("DailyPostCountClearScheduler - 일일게시글작성횟수 초기화 성공테스트")
    fun testClearDailyPostCount_positive() {
        dailyPostCountClearScheduler.clearDailyPostCount()
        verify(dailyPostCountRepository, times(1)).deleteAll()
    }
}