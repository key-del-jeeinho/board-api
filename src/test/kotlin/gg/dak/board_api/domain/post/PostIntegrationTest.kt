package gg.dak.board_api.domain.post

import gg.dak.board_api.IntegrationTestBase
import gg.dak.board_api.domain.post.data.request.CreatePostRequest
import gg.dak.board_api.domain.post.data.type.BoardType
import gg.dak.board_api.domain.post.data.type.CategoryType
import gg.dak.board_api.domain.post.repository.DailyPostCountRepository
import gg.dak.board_api.test_utils.TestComponentSource
import gg.dak.board_api.test_utils.TestUtil
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.notNullValue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class PostIntegrationTest: IntegrationTestBase() {
    @Autowired
    private lateinit var dailyPostCountRepository: DailyPostCountRepository

    @BeforeEach
    fun setUp() {
        TestComponentSource.initializeMockMvc(mvc)
        TestComponentSource.initializeObjectMapper(objectMapper)
    }

    @Test @DisplayName("게시글 작성 통합테스트 - 작성 성공")//게시글 작성
    fun testCreatePost() {
        //given
        dailyPostCountRepository.deleteAll()
        val title = TestUtil.data().post().title()
        val content = TestUtil.data().post().content()
        val category = CategoryType.values().random()
        val board = BoardType.values().random()
        val request = CreatePostRequest(title, content, category, board)

        //when
        val accessToken = TestUtil.query().account().accessToken("string", "string")
        val resultAction = mvc.perform(post("/api/v1/post")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
            .accept(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer $accessToken"))
            .andDo{ println(it) }

        //then
        resultAction
            .andExpect(status().isOk)
            .andExpect(jsonPath("idx", `is`(notNullValue())))
    }
}