package gg.dak.board_api.domain.post.controller

import gg.dak.board_api.TestDummyDataUtil
import gg.dak.board_api.domain.post.data.dto.PostQueryDto
import gg.dak.board_api.domain.post.data.event.PostQueryEvent
import gg.dak.board_api.domain.post.data.response.PageablePostSummeryQueryResponse
import gg.dak.board_api.domain.post.data.response.PostQueryResponse
import gg.dak.board_api.domain.post.data.response.PostSummeryQueryResponse
import gg.dak.board_api.domain.post.data.type.BoardType
import gg.dak.board_api.domain.post.service.PostQueryService
import gg.dak.board_api.domain.post.util.PostQueryConverter
import gg.dak.board_api.global.ip.service.RequestIpQueryService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import kotlin.math.absoluteValue
import kotlin.random.Random

class PostQueryControllerTest {
    private lateinit var postQueryConverter: PostQueryConverter
    private lateinit var postQueryService: PostQueryService
    private lateinit var requestIpQueryService: RequestIpQueryService
    private lateinit var applicationEventPublisher: ApplicationEventPublisher
    private lateinit var target: PostQueryController

    @BeforeEach
    fun setUp() {
        postQueryConverter = mock()
        postQueryService = mock()
        requestIpQueryService = mock()
        applicationEventPublisher = mock()
        target = PostQueryController(postQueryConverter, postQueryService, requestIpQueryService, applicationEventPublisher)
    }

    @Test @DisplayName("PostQueryController - 전체 게시글목록 조회 성공테스트")
    fun testFindAllPostWithPagination() {
        //given
        val page = Random.nextInt().absoluteValue
        val size = (1..100).random()
        val pagination = PageRequest.of(page, size)
        val posts = (1..size).map { TestDummyDataUtil.postQueryDto() }
        val data = PageImpl(posts)
        val response = mock<PostSummeryQueryResponse>()
        val pageableResponse = mock<PageablePostSummeryQueryResponse>()

        //when
        whenever(postQueryService.findAllPost(pagination)).thenReturn(data)
        whenever(postQueryConverter.toSummaryResponse(any())).thenReturn(response)
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
        val ip = TestDummyDataUtil.ip()
        val dto = mock<PostQueryDto>()
        val response = mock<PostQueryResponse>()
        val event = mock<PostQueryEvent>()

        //when
        whenever(postQueryService.findPostByIndex(idx)).thenReturn(dto)
        whenever(postQueryConverter.toResponse(dto)).thenReturn(response)
        whenever(requestIpQueryService.getCurrentRequestIp()).thenReturn(ip)
        whenever(postQueryConverter.toEvent(dto, ip)).thenReturn(event)

        //then
        val result = target.findPostByIndex(idx)
        assertTrue(result.statusCode.is2xxSuccessful)
        assertNotNull(result.body)
        assertEquals(result.body, response)
        verify(applicationEventPublisher, times(1)).publishEvent(event)
    }

    @Test @DisplayName("PostQueryController - 게시판별 게시글목록 조회 성공테스트")
    fun testFindAllPostByBoardWithPagination() {
        //given
        val board = BoardType.values().random()
        val page = Random.nextInt().absoluteValue
        val size = (1..100).random()
        val pagination = PageRequest.of(page, size)
        val posts = (1..size).map { TestDummyDataUtil.postQueryDto() }
        val data = PageImpl(posts)
        val response = mock<PostSummeryQueryResponse>()
        val pageableResponse = mock<PageablePostSummeryQueryResponse>()

        //when
        whenever(postQueryService.findAllPostByBoard(pagination, board)).thenReturn(data)
        whenever(postQueryConverter.toSummaryResponse(any())).thenReturn(response)
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
        val size = (1..100).random()
        val pagination = PageRequest.of(page, size)
        val posts = (1..size).map { TestDummyDataUtil.postQueryDto() }
        val data = PageImpl(posts)
        val response = mock<PostSummeryQueryResponse>()
        val pageableResponse = mock<PageablePostSummeryQueryResponse>()

        //when
        whenever(postQueryService.findAllPostByWriterIdx(pagination, writerIdx)).thenReturn(data)
        whenever(postQueryConverter.toSummaryResponse(any())).thenReturn(response)
        whenever(postQueryConverter.toPageableResponse(any())).thenReturn(pageableResponse)

        //then
        val result = target.findAllPostByWriterIdxWithPagination(writerIdx = writerIdx, page = page, size = size)
        assertTrue(result.statusCode.is2xxSuccessful)
        assertNotNull(result.body)
        assertEquals(result.body, pageableResponse)
    }
}