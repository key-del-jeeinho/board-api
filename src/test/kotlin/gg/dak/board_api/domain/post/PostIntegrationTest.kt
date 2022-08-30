package gg.dak.board_api.domain.post

import gg.dak.board_api.IntegrationTestBase
import gg.dak.board_api.domain.account.data.request.RegisterRequest
import gg.dak.board_api.domain.post.config.PostProperties
import gg.dak.board_api.domain.post.data.entity.DailyPostCount
import gg.dak.board_api.domain.post.data.request.CreatePostRequest
import gg.dak.board_api.domain.post.data.request.UpdatePostRequest
import gg.dak.board_api.domain.post.data.type.BoardType
import gg.dak.board_api.domain.post.data.type.CategoryType
import gg.dak.board_api.domain.post.repository.DailyPostCountRepository
import gg.dak.board_api.global.error.data.type.ErrorStatusType
import gg.dak.board_api.test_utils.TestComponentSource
import gg.dak.board_api.test_utils.TestUtil
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.notNullValue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class PostIntegrationTest: IntegrationTestBase() {
    @Autowired
    private lateinit var dailyPostCountRepository: DailyPostCountRepository
    @Autowired
    private lateinit var postProperties: PostProperties

    @BeforeEach
    fun setUp() {
        TestComponentSource.initializeMockMvc(mvc)
        TestComponentSource.initializeObjectMapper(objectMapper)
        TestComponentSource.initializeDailyPostCountRepository(dailyPostCountRepository)
    }
    @Test @DisplayName("게시글 작성 통합테스트 - 일일 작성한도를 초과하였을 경우")
    fun testCreatePost_일일_작성한도를_초과하였을_경우() {
        val nickname = TestUtil.data().account().nickname()
        val id = TestUtil.data().account().id()
        val password = TestUtil.data().account().password()

        val accountIdx = RegisterRequest(nickname, id, password).let { TestUtil.command().account().create(it) }
        val title = TestUtil.data().post().title()
        val content = TestUtil.data().post().content()
        val category = CategoryType.values().random()
        val board = BoardType.values().random()
        val request = CreatePostRequest(title, content, category, board)

        dailyPostCountRepository.deleteAll()
        DailyPostCount(accountIdx, postProperties.dailyPostLimit, board).let { TestUtil.command().post().saveDailyCount(it) }

        val accessToken = TestUtil.query().account().accessToken(id, password)
        val resultAction = mvc.perform(post("/api/v1/post")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
            .accept(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer $accessToken"))
            .andDo{ println(it.response.contentAsString) }

        resultAction
            .andExpect(status().`is`(HttpStatus.BAD_REQUEST.value()))
            .andExpect(jsonPath("status", `is`(ErrorStatusType.POLICY_VIOLATION.name)))
            .andExpect(jsonPath("message", `is`(notNullValue())))
            .andExpect(jsonPath("details", `is`(notNullValue())))
    }

    @Test @DisplayName("게시글 삭제 통합테스트 - 삭제 성공")
    fun testDeletePost() {
        val nickname = TestUtil.data().account().nickname()
        val id = TestUtil.data().account().id()
        val password = TestUtil.data().account().password()

        TestUtil.command().account().create(RegisterRequest(nickname, id, password))
        val accessToken = TestUtil.query().account().accessToken(id, password)

        val title = TestUtil.data().post().title()
        val content = TestUtil.data().post().content()
        val updatedContent = TestUtil.data().post().content()
        val category = CategoryType.values().random()
        val board = BoardType.values().random()

        val postIdx = TestUtil.command().post().create(CreatePostRequest(title, content, category, board), accessToken)

        mvc.perform(delete("/api/v1/post/$postIdx")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(UpdatePostRequest(updatedContent)))
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer $accessToken"))
                .andDo{ println(it.response.contentAsString) }
                .andExpect(status().`is`(HttpStatus.OK.value()))
                .andExpect(jsonPath("deletedPostIdx", `is`(notNullValue())))
    }

    @Test @DisplayName("게시글 수정 통합테스트 - 수정 성공")
    fun testUpdatePost() {
        val nickname = TestUtil.data().account().nickname()
        val id = TestUtil.data().account().id()
        val password = TestUtil.data().account().password()

        TestUtil.command().account().create(RegisterRequest(nickname, id, password))
        val accessToken = TestUtil.query().account().accessToken(id, password)

        val title = TestUtil.data().post().title()
        val content = TestUtil.data().post().content()
        val updatedContent = TestUtil.data().post().content()
        val category = CategoryType.values().random()
        val board = BoardType.values().random()

        val postIdx = TestUtil.command().post().create(CreatePostRequest(title, content, category, board), accessToken)

        mvc.perform(put("/api/v1/post/$postIdx")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(UpdatePostRequest(updatedContent)))
            .accept(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer $accessToken"))
            .andDo{ println(it.response.contentAsString) }
            .andExpect(status().`is`(HttpStatus.OK.value()))
                .andExpect(jsonPath("updatedPostIdx", `is`(notNullValue())))
    }

    @Test @DisplayName("게시글 작성 통합테스트 - 작성 성공")
    fun testCreatePost() {
        val nickname = TestUtil.data().account().nickname()
        val id = TestUtil.data().account().id()
        val password = TestUtil.data().account().password()

        TestUtil.command().account().create(RegisterRequest(nickname, id, password))
        val title = TestUtil.data().post().title()
        val content = TestUtil.data().post().content()
        val category = CategoryType.values().random()
        val board = BoardType.values().random()
        val request = CreatePostRequest(title, content, category, board)

        dailyPostCountRepository.deleteAll()

        val accessToken = TestUtil.query().account().accessToken(id, password)
        val resultAction = mvc.perform(post("/api/v1/post")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
            .accept(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer $accessToken"))
            .andDo{ println(it.response.contentAsString) }

        resultAction
            .andExpect(status().isOk)
            .andExpect(jsonPath("idx", `is`(notNullValue())))
    }
}