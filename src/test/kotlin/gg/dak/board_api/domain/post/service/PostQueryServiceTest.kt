package gg.dak.board_api.domain.post.service

import gg.dak.board_api.domain.post.data.dto.PostDto
import gg.dak.board_api.domain.post.data.entity.Post
import gg.dak.board_api.domain.post.repository.PostRepository
import gg.dak.board_api.domain.post.util.PostConverter
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.util.*
import kotlin.random.Random

class PostQueryServiceTest {
    private lateinit var postRepository: PostRepository
    private lateinit var postConverter: PostConverter
    private lateinit var target: PostQueryService

    @BeforeEach
    fun setUp() {
        postRepository = mock()
        postConverter = mock()
        target = PostQueryServiceImpl(postRepository, postConverter)
    }

    @Test @DisplayName("PostQueryService - 인덱스로 게시글 조회 성공테스트")
    fun testFindAccountByIndex_positive() {
        //given
        val idx = Random.nextLong()
        val entity = mock<Post>()
        val optional = Optional.of(entity)
        val dto = mock<PostDto>()

        //when
        whenever(postRepository.findById(idx)).thenReturn(optional)
        whenever(postConverter.toDto(entity)).thenReturn(dto)

        //then
        val result = target.findPostByIndex(idx)
        Assertions.assertEquals(result, dto)
    }
}