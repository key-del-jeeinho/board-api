package gg.dak.board_api.domain.post.service

import gg.dak.board_api.domain.post.data.dto.PostDto
import gg.dak.board_api.domain.post.data.entity.Post
import gg.dak.board_api.domain.post.data.type.PostOperationType
import gg.dak.board_api.domain.post.repository.PostRepository
import gg.dak.board_api.domain.post.util.PostConverter
import gg.dak.board_api.domain.post.util.PostProcessor
import gg.dak.board_api.domain.post.util.PostValidator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class PostServiceTest {
    private lateinit var postConverter: PostConverter
    private lateinit var postRepository: PostRepository
    private lateinit var postValidator: PostValidator
    private lateinit var postProcessor: PostProcessor
    private lateinit var target: PostService

    @BeforeEach
    fun setUp() {
        postConverter = mock()
        postRepository = mock()
        postValidator = mock()
        postProcessor = mock()
        target = PostServiceImpl(postConverter, postRepository, postValidator, postProcessor)
    }

    @Test @DisplayName("PostService - 포스트 생성 성공테스트")
    fun testCreatePost_positive() {
        //given
        val dto = mock<PostDto>()
        val processedDto = mock<PostDto>()
        val entity = mock<Post>()
        val savedEntity = mock<Post>()
        val savedDto = mock<PostDto>()

        //when
        whenever(postProcessor.process(PostOperationType.CREATE, dto)).thenReturn(processedDto)
        whenever(postConverter.toEntity(processedDto)).thenReturn(entity)
        whenever(postRepository.save(entity)).thenReturn(savedEntity)
        whenever(postConverter.toDto(savedEntity)).thenReturn(savedDto)

        //then
        val result = target.createPost(dto)
        assertEquals(result, savedDto)
        verify(postValidator, times(1)).validate(PostOperationType.CREATE, dto)
    }
}