package gg.dak.board_api.domain.post.controller

import gg.dak.board_api.domain.post.data.dto.PostDto
import gg.dak.board_api.domain.post.data.response.PostQueryResponse
import gg.dak.board_api.domain.post.service.PostQueryService
import gg.dak.board_api.domain.post.util.PostQueryConverter
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
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
        Assertions.assertTrue(result.statusCode.is2xxSuccessful)
        Assertions.assertNotNull(result.body)
        assertEquals(result.body, response)
    }
}