package gg.dak.board_api.domain.post.service

import gg.dak.board_api.domain.post.data.dto.PostDto
import gg.dak.board_api.domain.post.data.dto.PostQueryDto
import gg.dak.board_api.domain.post.data.entity.Post
import gg.dak.board_api.domain.post.data.type.BoardType
import gg.dak.board_api.domain.post.repository.PostRepository
import gg.dak.board_api.domain.post.repository.PostViewCountRepository
import gg.dak.board_api.domain.post.util.PostConverter
import gg.dak.board_api.domain.post.util.PostQueryConverter
import gg.dak.board_api.test_utils.TestUtil
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import java.util.*
import kotlin.math.absoluteValue
import kotlin.random.Random

class PostQueryServiceTest {
    private lateinit var postRepository: PostRepository
    private lateinit var postConverter: PostConverter
    private lateinit var postViewCountRepository: PostViewCountRepository
    private lateinit var postQueryConverter: PostQueryConverter
    private lateinit var target: PostQueryService

    @BeforeEach
    fun setUp() {
        postRepository = mock()
        postConverter = mock()
        postViewCountRepository = mock()
        postQueryConverter = mock()
        target = PostQueryServiceImpl(postRepository, postConverter, postViewCountRepository, postQueryConverter)
    }

    @Test @DisplayName("PostQueryService - 전체 게시글목록 조회 성공테스트")
    fun testFindAllPost_positive() {
        //given
        val page = Random.nextInt().absoluteValue
        val size = (1..100).random()
        val pagination = PageRequest.of(page, size)
        val posts = (1..size).map { TestUtil.data().post().entity() }
        val data = PageImpl(posts)
        val dto = mock<PostDto>()
        val queryDto = mock<PostQueryDto>()

        //when
        whenever(postRepository.findBy(pagination)).thenReturn(data)
        whenever(postConverter.toDto(any<Post>())).thenReturn(dto)
        whenever(postViewCountRepository.findById(any())).thenReturn(Optional.empty())
        whenever(postQueryConverter.toQueryDto(any(), eq(dto))).thenReturn(queryDto)

        //then
        val result = target.findAllPost(pagination)
        assertTrue(result.content.stream().allMatch { it == queryDto })
    }

    @Test @DisplayName("PostQueryService - 인덱스로 게시글 조회 성공테스트")
    fun testFindPostByIndex_positive() {
        //given
        val idx = Random.nextLong()
        val entity = mock<Post>()
        val optional = Optional.of(entity)
        val dto = mock<PostDto>()
        val queryDto = mock<PostQueryDto>()

        //when
        whenever(postRepository.findById(idx)).thenReturn(optional)
        whenever(postConverter.toDto(entity)).thenReturn(dto)
        whenever(postViewCountRepository.findById(any())).thenReturn(Optional.empty())
        whenever(postQueryConverter.toQueryDto(any(), eq(dto))).thenReturn(queryDto)

        //then
        val result = target.findPostByIndex(idx)
        assertEquals(result, queryDto)
    }

    @Test @DisplayName("PostQueryService - 게시판별 게시글목록 조회 성공테스트")
    fun testFindAllPostByBoard_positive() {
        //given
        val board = BoardType.values().random()
        val page = Random.nextInt().absoluteValue
        val size = (1..100).random()
        val pagination = PageRequest.of(page, size)
        val posts = (1..size).map { TestUtil.data().post().entity() }
        val data = PageImpl(posts)
        val dto = mock<PostDto>()
        val queryDto = mock<PostQueryDto>()

        //when
        whenever(postRepository.findAllByBoard(pagination, board)).thenReturn(data)
        whenever(postConverter.toDto(any<Post>())).thenReturn(dto)
        whenever(postViewCountRepository.findById(any())).thenReturn(Optional.empty())
        whenever(postQueryConverter.toQueryDto(any(), eq(dto))).thenReturn(queryDto)

        //then
        val result = target.findAllPostByBoard(pagination, board)
        assertTrue(result.content.stream().allMatch { it == queryDto })
    }

    @Test @DisplayName("PostQueryService - 작성자별 게시글목록 조회 성공테스트")
    fun testFindAllPostByWriterIdx_positive() {
        //given
        val writerIdx = Random.nextLong()
        val page = Random.nextInt().absoluteValue
        val size = (1..100).random()
        val pagination = PageRequest.of(page, size)
        val posts = (1..size).map { TestUtil.data().post().entity() }
        val data = PageImpl(posts)
        val dto = mock<PostDto>()
        val queryDto = mock<PostQueryDto>()

        //when
        whenever(postRepository.findAllByWriterIdx(pagination, writerIdx)).thenReturn(data)
        whenever(postConverter.toDto(any<Post>())).thenReturn(dto)
        whenever(postViewCountRepository.findById(any())).thenReturn(Optional.empty())
        whenever(postQueryConverter.toQueryDto(any(), eq(dto))).thenReturn(queryDto)

        //then
        val result = target.findAllPostByWriterIdx(pagination, writerIdx)
        assertTrue(result.content.stream().allMatch { it == queryDto })
    }
}