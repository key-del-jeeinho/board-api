package gg.dak.board_api.domain.post.controller

import gg.dak.board_api.domain.post.data.dto.PostDto
import gg.dak.board_api.domain.post.data.request.CreatePostRequest
import gg.dak.board_api.domain.post.data.response.CreatePostResponse
import gg.dak.board_api.domain.post.service.PostService
import gg.dak.board_api.domain.post.util.PostConverter
import gg.dak.board_api.global.security.service.LoginAccountService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.random.Random

class PostControllerTest {
    private lateinit var postService: PostService
    private lateinit var postConverter: PostConverter
    private lateinit var loginAccountService: LoginAccountService
    private lateinit var target: PostController

    @BeforeEach
    fun setUp() {
        postService = mock()
        postConverter = mock()
        loginAccountService = mock()
        target = PostController(postService, postConverter, loginAccountService)
    }
    
    @Test @DisplayName("PostController - 포스트 생성 성공테스트")
    fun testCreatePost_positive() {
        //given
        val request = mock<CreatePostRequest>()
        val writerIdx = Random.nextLong()
        val dto = mock<PostDto>()
        val createdDto = mock<PostDto>()
        val response = mock<CreatePostResponse>()

        //when
        whenever(loginAccountService.getLoginAccount()).thenReturn(mock())
        whenever(loginAccountService.getLoginAccount().idx).thenReturn(writerIdx)
        whenever(postConverter.toDto(request, writerIdx)).thenReturn(dto)
        whenever(postService.createPost(dto)).thenReturn(createdDto)
        whenever(postConverter.toResponse(createdDto)).thenReturn(response)

        //then
        val result = target.createPost(request)
        assertTrue(result.statusCode.is2xxSuccessful)
        assertNotNull(result.body)
        assertEquals(result.body, response)
    }
}