package gg.dak.board_api.domain.post.util

import gg.dak.board_api.domain.post.data.dto.PostDto
import gg.dak.board_api.domain.post.data.type.PostOperationType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class PostProcessorTest {
    private lateinit var postProcessor: PostProcessor

    @BeforeEach
    fun setUp() {
        postProcessor = PostProcessorImpl()
    }

    @Test @DisplayName("PostProcessor - 포스트 생성 전처리 로직 성공테스트")
    fun testProcessCreatePost_positive() {
        //given
        val dto = mock<PostDto>()
        val processedDto = mock<PostDto>()

        //when
        whenever(dto.copy(idx = 0)).thenReturn(processedDto)

        //then
        val result = postProcessor.process(PostOperationType.CREATE, dto)
        assertEquals(result, processedDto)
    }
}