package gg.dak.board_api.domain.post.service

import gg.dak.board_api.domain.post.data.dto.PostDto
import gg.dak.board_api.domain.post.data.entity.Post
import gg.dak.board_api.domain.post.data.event.PostCreateEvent
import gg.dak.board_api.domain.post.data.event.PostDeleteEvent
import gg.dak.board_api.domain.post.data.event.PostUpdateEvent
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
import org.springframework.context.ApplicationEventPublisher
import kotlin.random.Random

class PostServiceTest {
    private lateinit var postConverter: PostConverter
    private lateinit var postRepository: PostRepository
    private lateinit var postValidator: PostValidator
    private lateinit var postProcessor: PostProcessor
    private lateinit var applicationEventPublisher: ApplicationEventPublisher
    private lateinit var target: PostService

    @BeforeEach
    fun setUp() {
        postConverter = mock()
        postRepository = mock()
        postValidator = mock()
        postProcessor = mock()
        applicationEventPublisher = mock()
        target = PostServiceImpl(postConverter, postRepository, postValidator, postProcessor, applicationEventPublisher)
    }

    @Test @DisplayName("PostService - 포스트 생성 성공테스트")
    fun testCreatePost_positive() {
        //given
        val dto = mock<PostDto>()
        val processedDto = mock<PostDto>()
        val entity = mock<Post>()
        val savedEntity = mock<Post>()
        val savedDto = mock<PostDto>()
        val event = mock<PostCreateEvent>()

        //when
        whenever(postProcessor.process(PostOperationType.CREATE, dto)).thenReturn(processedDto)
        whenever(postConverter.toEntity(processedDto)).thenReturn(entity)
        whenever(postRepository.save(entity)).thenReturn(savedEntity)
        whenever(postConverter.toDto(savedEntity)).thenReturn(savedDto)
        whenever(postConverter.toCreateEvent(savedDto)).thenReturn(event)

        //then
        val result = target.createPost(dto)
        assertEquals(result, savedDto)
        verify(postValidator, times(1)).validate(PostOperationType.CREATE, dto)
        verify(applicationEventPublisher, times(1)).publishEvent(event)
    }

    @Test @DisplayName("PostService - 포스트 제거 성공테스트")
    fun testDeletePost_positive() {
        //given
        val idx = Random.nextLong()
        val dto = mock<PostDto>()
        val processedDto = mock<PostDto>()
        val event = mock<PostDeleteEvent>()

        //when
        whenever(postProcessor.process(PostOperationType.DELETE, dto)).thenReturn(processedDto)
        whenever(processedDto.idx).thenReturn(idx)
        whenever(postConverter.toDeleteEvent(idx)).thenReturn(event)

        //then
        val result = target.deletePost(dto)
        assertEquals(result, dto)
        verify(postValidator, times(1)).validate(PostOperationType.DELETE, dto)
        verify(applicationEventPublisher, times(1)).publishEvent(event)
    }

    @Test @DisplayName("PostService - 포스트 수정 성공테스트")
    fun testUpdatePost_positive() {
        //given
        val dto = mock<PostDto>()
        val entity = mock<Post>()
        val savedEntity = mock<Post>()
        val processedDto = mock<PostDto>()
        val savedDto = mock<PostDto>()
        val event = mock<PostUpdateEvent>()

        //when
        whenever(postProcessor.process(PostOperationType.UPDATE, dto)).thenReturn(processedDto) //전처리과정에서 수정된 포스트 정보를 반환한다.
        whenever(postConverter.toEntity(processedDto)).thenReturn(entity)
        whenever(postRepository.save(entity)).thenReturn(savedEntity)
        whenever(postConverter.toDto(savedEntity)).thenReturn(savedDto)
        whenever(postConverter.toUpdateEvent(savedDto)).thenReturn(event)

        //then
        val result = target.updatePost(dto)
        assertEquals(result, savedDto)
        verify(postValidator, times(1)).validate(PostOperationType.UPDATE, dto)
        verify(applicationEventPublisher, times(1)).publishEvent(event)
    }
}