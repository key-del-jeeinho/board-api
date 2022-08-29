package gg.dak.board_api.domain.post.util

import gg.dak.board_api.domain.post.data.dto.PostDto
import gg.dak.board_api.domain.post.data.type.PostOperationType
import gg.dak.board_api.domain.post.repository.PostRepository
import gg.dak.board_api.test_utils.TestUtil
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.util.*

class PostProcessorTest {
    private lateinit var postRepository: PostRepository
    private lateinit var postConverter: PostConverter
    private lateinit var target: PostProcessor

    @BeforeEach
    fun setUp() {
        postRepository = mock()
        postConverter = PostConverterImpl()
        target = PostProcessorImpl(postRepository, postConverter)
    }

    @Test @DisplayName("PostProcessor - 포스트 생성 전처리 로직 성공테스트")
    fun testProcessCreatePost_positive() {
        //given
        val dto = mock<PostDto>()
        val processedDto = mock<PostDto>()

        //when
        whenever(dto.copy(idx = 0)).thenReturn(processedDto)

        //then
        val result = target.process(PostOperationType.CREATE, dto)
        assertEquals(result, processedDto)
    }

    @Test @DisplayName("PostProcessor - 포스트 제거 전처리 로직 성공테스트")
    fun testProcessDeletePost_positive() {
        //given
        val dto = mock<PostDto>()

        //then
        val result = target.process(PostOperationType.DELETE, dto)
        assertEquals(result, dto)
    }

    @Test @DisplayName("PostProcessor - 포스트 수정 전처리 로직 성공테스트")
    fun testProcessUpdatePost_positive() {
        //given
        val dto = mock<PostDto>()
        val updateContent = TestUtil.data().post().content()
        val entity = TestUtil.data().post().entity()
        val idx = entity.idx
        val updatedDto = TestUtil.convert().post().updatePost(entity, updateContent).let { TestUtil.convert().post().toDto(it) }
        val optional = Optional.of(entity)

        //when
        whenever(dto.idx).thenReturn(idx)
        whenever(dto.content).thenReturn(updateContent)
        whenever(postRepository.findById(dto.idx)).thenReturn(optional)

        //then
        val result = target.process(PostOperationType.UPDATE, dto)
        verify(postRepository, times(1)).findById(dto.idx)
        assertEquals(result, updatedDto)
    }
}