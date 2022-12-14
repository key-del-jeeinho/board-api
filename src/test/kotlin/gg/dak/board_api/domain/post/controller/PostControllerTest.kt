package gg.dak.board_api.domain.post.controller

import gg.dak.board_api.domain.post.data.dto.PostDto
import gg.dak.board_api.domain.post.data.request.CreatePostRequest
import gg.dak.board_api.domain.post.data.request.UpdatePostRequest
import gg.dak.board_api.domain.post.data.response.CreatePostResponse
import gg.dak.board_api.domain.post.data.response.DeletePostResponse
import gg.dak.board_api.domain.post.data.response.UpdatePostResponse
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
        whenever(postConverter.toCreateResponse(createdDto)).thenReturn(response)

        //then
        val result = target.createPost(request)
        assertTrue(result.statusCode.is2xxSuccessful)
        assertNotNull(result.body)
        assertEquals(result.body, response)
    }

    @Test @DisplayName("PostController - 포스트 제거 성공테스트")
    fun testDeletePost_positive() {
        //given
        val idx = Random.nextLong()
        val dto = mock<PostDto>()
        val deletedDto = mock<PostDto>()
        val response = mock<DeletePostResponse>()

        //when
        whenever(postConverter.toDto(idx)).thenReturn(dto)
        whenever(postService.deletePost(dto)).thenReturn(deletedDto)
        whenever(postConverter.toDeleteResponse(deletedDto)).thenReturn(response)

        //then
        val result = target.deletePost(idx)
        assertTrue(result.statusCode.is2xxSuccessful)
        assertNotNull(result.body)
        assertEquals(result.body, response)
    }

    @Test @DisplayName("PostController - 포스트 수정 성공테스트")
    fun testUpdatePost_positive() {
        //given
        val idx = Random.nextLong()
        val request = mock<UpdatePostRequest>()
        val dto = mock<PostDto>()
        val updatedDto = mock<PostDto>()
        val response = mock<UpdatePostResponse>()

        //when
        whenever(postConverter.toDto(idx, request)).thenReturn(dto)
        whenever(postService.updatePost(dto)).thenReturn(updatedDto)
        whenever(postConverter.toUpdateResponse(updatedDto)).thenReturn(response)

        //then
        val result = target.updatePost(idx, request)
        assertTrue(result.statusCode.is2xxSuccessful)
        assertNotNull(result.body)
        assertEquals(result.body, response)
    }
}