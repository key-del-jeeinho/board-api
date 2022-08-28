package gg.dak.board_api.domain.post.listener

import gg.dak.board_api.TestDummyDataUtil
import gg.dak.board_api.domain.post.data.entity.PostViewCount
import gg.dak.board_api.domain.post.data.event.PostQueryEvent
import gg.dak.board_api.domain.post.repository.PostViewCountRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.util.*
import kotlin.random.Random

class PostViewCountUpdateListenerTest {
    private lateinit var postViewCountRepository: PostViewCountRepository
    private lateinit var target: PostViewCountUpdateListener

    @BeforeEach
    fun setUp() {
        postViewCountRepository = mock()
        target = PostViewCountUpdateListener(postViewCountRepository)
    }

    @Test @DisplayName("PostViewCountUpdateListener - 게시글 조회수 추가 성공테스트A")
    fun testUpdatePostViewCount_positiveA() { //만약 이미 게시글 조회수 정보가 존재할 경우
        //given
        val idx = Random.nextLong()
        val viewCount = (1..100).random()
        val ip = TestDummyDataUtil.ip()
        val ips = (1..viewCount).map { TestDummyDataUtil.ip() }.filter { it != ip }
        val event = mock<PostQueryEvent>()
        val entity = PostViewCount(idx, (ips).toSet())
        val optional = Optional.of(entity)
        val newEntity = PostViewCount(idx, (ips + ip).toSet())
        val savedEntity = mock<PostViewCount>()

        //when
        whenever(event.idx).thenReturn(idx)
        whenever(event.ip).thenReturn(ip)
        whenever(postViewCountRepository.existsById(idx)).thenReturn(true)
        whenever(postViewCountRepository.findById(idx)).thenReturn(optional)
        whenever(postViewCountRepository.save(newEntity)).thenReturn(savedEntity)

        //then
        target.handle(event)
        verify(postViewCountRepository, times(1)).save(newEntity)
    }
}