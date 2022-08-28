package gg.dak.board_api.domain.post.controller

import gg.dak.board_api.TestDummyDataUtil
import gg.dak.board_api.domain.post.data.dto.PostDto
import gg.dak.board_api.domain.post.data.response.PageablePostQueryResponse
import gg.dak.board_api.domain.post.data.response.PostQueryResponse
import gg.dak.board_api.domain.post.data.type.BoardType
import gg.dak.board_api.domain.post.service.PostQueryService
import gg.dak.board_api.domain.post.util.PostQueryConverter
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import kotlin.math.absoluteValue
import kotlin.random.Random

class PostQueryControllerTest {
    private lateinit var postQueryConverter: PostQueryConverter
    private lateinit var postQueryService: PostQueryService
    private lateinit var target: PostQueryController

    @BeforeEach
    fun setUp() {
        postQueryConverter = mock()
        postQueryService = mock()
        target = PostQueryController(postQueryConverter, postQueryService)
    }

    @Test @DisplayName("PostQueryController - 전체 게시글목록 조회 성공테스트")
    fun testFindAllPostWithPagination() {
        //given
        val page = Random.nextInt().absoluteValue
        val size = (0..100).random()
        val pagination = PageRequest.of(page, size)
        val posts = (1..size).map { TestDummyDataUtil.postDto() }
        val data = PageImpl(posts)
        val response = mock<PostQueryResponse>()
        val pageableResponse = mock<PageablePostQueryResponse>()

        //when
        whenever(postQueryService.findAllPost(pagination)).thenReturn(data)
        whenever(postQueryConverter.toResponse(any())).thenReturn(response)
        whenever(postQueryConverter.toPageableResponse(any())).thenReturn(pageableResponse)

        //then
        val result = target.findAllPostWithPagination(page = page, size = size)
        assertTrue(result.statusCode.is2xxSuccessful)
        assertNotNull(result.body)
        assertEquals(result.body, pageableResponse)
    }

    @Test @DisplayName("PostQueryController - 인덱스로 게시글 조회 성공테스트")
    fun testFindPostByIndex() {
        //given
        val idx = Random.nextLong()
        val dto = mock<PostDto>()
        val response = mock<PostQueryResponse>()

        //when
        whenever(postQueryService.findPostByIndex(idx)).thenReturn(dto)
        whenever(postQueryConverter.toResponse(dto)).thenReturn(response)

        //then
        val result = target.findPostByIndex(idx)
        assertTrue(result.statusCode.is2xxSuccessful)
        assertNotNull(result.body)
        assertEquals(result.body, response)
    }

    @Test @DisplayName("PostQueryController - 게시판별 게시글목록 조회 성공테스트")
    fun testFindAllPostByBoardWithPagination() {
        //given
        val board = BoardType.values().random()
        val page = Random.nextInt().absoluteValue
        val size = (0..100).random()
        val pagination = PageRequest.of(page, size)
        val posts = (1..size).map { TestDummyDataUtil.postDto() }
        val data = PageImpl(posts)
        val response = mock<PostQueryResponse>()
        val pageableResponse = mock<PageablePostQueryResponse>()

        //when
        whenever(postQueryService.findAllPostByBoard(pagination, board)).thenReturn(data)
        whenever(postQueryConverter.toResponse(any())).thenReturn(response)
        whenever(postQueryConverter.toPageableResponse(any())).thenReturn(pageableResponse)

        //then
        val result = target.findAllPostByBoardWithPagination(board = board, page = page, size = size)
        assertTrue(result.statusCode.is2xxSuccessful)
        assertNotNull(result.body)
        assertEquals(result.body, pageableResponse)
    }

    @Test @DisplayName("PostQueryController - 작성자별 게시글목록 조회 성공테스트")
    fun testFindAllPostsByWriterIdxWithPagination() {
        //given
        val writerIdx = Random.nextLong()
        val page = Random.nextInt().absoluteValue
        val size = (0..100).random()
        val pagination = PageRequest.of(page, size)
        val posts = (1..size).map { TestDummyDataUtil.postDto() }
        val data = PageImpl(posts)
        val response = mock<PostQueryResponse>()
        val pageableResponse = mock<PageablePostQueryResponse>()

        //when
        whenever(postQueryService.findAllPostByWriterIdx(pagination, writerIdx)).thenReturn(data)
        whenever(postQueryConverter.toResponse(any())).thenReturn(response)
        whenever(postQueryConverter.toPageableResponse(any())).thenReturn(pageableResponse)

        //then
        val result = target.findAllPostByWriterIdxWithPagination(writerIdx = writerIdx, page = page, size = size)
        assertTrue(result.statusCode.is2xxSuccessful)
        assertNotNull(result.body)
        assertEquals(result.body, pageableResponse)
    }
}