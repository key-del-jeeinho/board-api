package gg.dak.board_api.domain.post.util

import gg.dak.board_api.domain.post.config.PostProperties
import gg.dak.board_api.domain.post.data.dto.PostDto
import gg.dak.board_api.domain.post.data.entity.DailyPostCount
import gg.dak.board_api.domain.post.data.type.BoardType
import gg.dak.board_api.domain.post.data.type.PostOperationType
import gg.dak.board_api.domain.post.repository.DailyPostCountRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.util.*
import kotlin.random.Random

class PostValidatorTest {
    private lateinit var postProperties: PostProperties
    private lateinit var dailyPostCountRepository: DailyPostCountRepository
    private lateinit var target: PostValidator

    @BeforeEach
    fun setUp() {
        postProperties = mock()
        dailyPostCountRepository = mock()
        target = PostValidatorImpl(postProperties, dailyPostCountRepository)
    }

    /* PolicyValidator - 게시글 생성 로즉 검증 성공테스트
    PolicyValidator.validate(PostOperationType.CREATE, ?: PostDto)
    해당 게시판에 대해, 작성자가 일일 작성 가능 횟수를 초과했는지 검증한다.
     */
    @Test @DisplayName("PolicyValidator - 게시글 생성 로직 검증 성공테스트A")
    fun testValidateCreatePost_positive() {
        //given
        val board = BoardType.values().random()
        val dailyPostLimit = (1..1000).random()
        val count = (1..999).filter { dailyPostLimit > it }.random()
        val accountIdx = Random.nextLong()
        val dto = mock<PostDto>()
        val countEntity = mock<DailyPostCount>()
        val optional = Optional.of(countEntity)

        //when
        whenever(postProperties.dailyPostLimit).thenReturn(dailyPostLimit)
        whenever(dailyPostCountRepository.findByAccountIdxAndBoard(accountIdx, board)).thenReturn(optional)
        whenever(countEntity.count).thenReturn(count)
        whenever(countEntity.board).thenReturn(board)

        //then
        assertDoesNotThrow { target.validate(PostOperationType.CREATE, dto) }
    }
}