package gg.dak.board_api.domain.post.listener

import gg.dak.board_api.domain.post.data.entity.DailyPostCount
import gg.dak.board_api.domain.post.data.event.PostCreateEvent
import gg.dak.board_api.domain.post.data.type.BoardType
import gg.dak.board_api.domain.post.repository.DailyPostCountRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.util.Optional
import kotlin.math.absoluteValue
import kotlin.random.Random

class DailyPostCountUpdateListenerTest {
    private lateinit var dailyPostCountRepository: DailyPostCountRepository
    private lateinit var target: DailyPostCountUpdateListener

    @BeforeEach
    fun setUp() {
        dailyPostCountRepository = mock()
        target = DailyPostCountUpdateListener(dailyPostCountRepository)
    }

    @Test @DisplayName("DailyPostCountUpdateListener - 일일 게시글 작성 횟수 추가 성공테스트A")
    fun testUpdateDailyPostCount_positiveA() { //만약 이미 일일 작성 횟수 정보가 존재할 경우
        //given
        val accountIdx = Random.nextLong()
        val board = BoardType.values().random()
        val count = Random.nextInt().absoluteValue
        val event = mock<PostCreateEvent>()
        val entity = mock<DailyPostCount>()
        val optional = Optional.of(entity)
        val newEntity = mock<DailyPostCount>()
        val savedEntity = mock<DailyPostCount>()

        //when
        whenever(event.writerIdx).thenReturn(accountIdx)
        whenever(event.board).thenReturn(board)
        whenever(dailyPostCountRepository.existsByAccountIdxAndBoard(accountIdx, board)).thenReturn(true)
        whenever(dailyPostCountRepository.findByAccountIdxAndBoard(accountIdx, board)).thenReturn(optional)
        whenever(entity.count).thenReturn(count)
        whenever(entity.copy(count = count+1)).thenReturn(newEntity)
        whenever(dailyPostCountRepository.save(newEntity)).thenReturn(savedEntity)

        //then
        target.handle(event)
        verify(dailyPostCountRepository, times(1)).save(newEntity)
    }

    @Test @DisplayName("DailyPostCountUpdateListener - 일일 게시글 작성 횟수 추가 성공테스트B")
    fun testUpdateDailyPostCount_positiveB() { //만약 일일 작성 횟수 정보가 존재하지 않을 경우
        //given
        val accountIdx = Random.nextLong()
        val board = BoardType.values().random()
        val event = mock<PostCreateEvent>()
        val entity = DailyPostCount(accountIdx, 1, board)

        //when
        whenever(event.writerIdx).thenReturn(accountIdx)
        whenever(event.board).thenReturn(board)
        whenever(dailyPostCountRepository.existsByAccountIdxAndBoard(accountIdx, board)).thenReturn(false)

        //then
        target.handle(event)
        verify(dailyPostCountRepository, times(1)).save(entity)
    }
}