package gg.dak.board_api.domain.post

import gg.dak.board_api.IntegrationTestBase
import gg.dak.board_api.domain.account.util.JwtTokenUtil
import gg.dak.board_api.domain.post.config.PostProperties
import gg.dak.board_api.domain.post.data.entity.DailyPostCount
import gg.dak.board_api.domain.post.data.request.CreatePostRequest
import gg.dak.board_api.domain.post.data.request.UpdatePostRequest
import gg.dak.board_api.domain.post.data.type.BoardType
import gg.dak.board_api.domain.post.data.type.CategoryType
import gg.dak.board_api.domain.post.repository.DailyPostCountRepository
import gg.dak.board_api.domain.post.repository.PostRepository
import gg.dak.board_api.global.account.repository.AccountRepository
import gg.dak.board_api.global.error.data.type.ErrorStatusType
import gg.dak.board_api.test_utils.TestComponentSource
import gg.dak.board_api.test_utils.TestEnvironment.createAccessToken
import gg.dak.board_api.test_utils.TestEnvironment.createAccount
import gg.dak.board_api.test_utils.TestEnvironment.createPost
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
import kotlin.math.absoluteValue
import kotlin.random.Random

class PostIntegrationTest: IntegrationTestBase() {
    @Autowired
    private lateinit var dailyPostCountRepository: DailyPostCountRepository
    @Autowired
    private lateinit var postProperties: PostProperties
    @Autowired
    private lateinit var accountRepository: AccountRepository
    @Autowired
    private lateinit var jwtTokenUtil: JwtTokenUtil
    @Autowired
    private lateinit var postRepository: PostRepository

    @BeforeEach
    fun setUp() {
        TestComponentSource.initializeMockMvc(mvc)
        TestComponentSource.initializeObjectMapper(objectMapper)
        TestComponentSource.initializeDailyPostCountRepository(dailyPostCountRepository)
        TestComponentSource.initializeAccountRepository(accountRepository)
        TestComponentSource.initializeJwtTokenGenerator(jwtTokenUtil)

        dailyPostCountRepository.deleteAll()
        accountRepository.deleteAll()
        postRepository.deleteAll()
    }

    @Test @DisplayName("게시글 삭제 통합테스트 - 삭제 성공")
    fun testDeletePost() {
        val account = createAccount()
        val accessToken = createAccessToken(account)
        val post = createPost(accessToken)

        mvc.perform(delete("/api/v1/post/${post.idx}")
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer $accessToken"))
                .andDo{ println(it.response.contentAsString) }
                .andExpect(status().`is`(HttpStatus.OK.value()))
                .andExpect(jsonPath("deletedPostIdx", `is`(notNullValue())))
    }

    @Test @DisplayName("게시글 삭제 통합테스트 - 게시글이 존재하지 않을 경우")
    fun testDeletePost_게시글이_존재하지_않을_경우() {
        val account = createAccount()
        val accessToken = createAccessToken(account)
        val postIdx = Random.nextLong().absoluteValue

        mvc.perform(delete("/api/v1/post/${postIdx}")
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer $accessToken"))
                .andDo{ println(it.response.contentAsString) }
                .andExpect(status().`is`(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("status", `is`(ErrorStatusType.POLICY_VIOLATION.name)))
                .andExpect(jsonPath("message", `is`(notNullValue())))
                .andExpect(jsonPath("details", `is`(notNullValue())))
    }

    @Test @DisplayName("게시글 삭제 통합테스트 - 게시글 작성자가 아닐경우")
    fun testDeletePost_게시글_작성자가_아닐경우() {
        val writerAccount = createAccount()
        val writerAccessToken = createAccessToken(writerAccount)
        val postIdx = createPost(writerAccessToken).idx
        val issuerAccount = createAccount()
        val issuerAccessToken = createAccessToken(issuerAccount)

        mvc.perform(delete("/api/v1/post/${postIdx}")
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer $issuerAccessToken"))
                .andDo { println(it.response.contentAsString) }
                .andExpect(status().`is`(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("status", `is`(ErrorStatusType.POLICY_VIOLATION.name)))
                .andExpect(jsonPath("message", `is`(notNullValue())))
                .andExpect(jsonPath("details", `is`(notNullValue())))
    }

    @Test @DisplayName("게시글 수정 통합테스트 - 수정 성공")
    fun testUpdatePost() {
        val account = createAccount()
        val accessToken = createAccessToken(account)
        val post = createPost(accessToken)

        val updatedContent = TestUtil.data().post().content()

        mvc.perform(put("/api/v1/post/${post.idx}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(UpdatePostRequest(updatedContent)))
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer $accessToken"))
                .andDo{ println(it.response.contentAsString) }
                .andExpect(status().`is`(HttpStatus.OK.value()))
                .andExpect(jsonPath("updatedPostIdx", `is`(notNullValue())))
    }

    @Test @DisplayName("게시글 수정 통합테스트 - 게시글이 존재하지 않을 경우")
    fun testUpdatePost_게시글이_존재하지_않을_경우() {
        val account = createAccount()
        val accessToken = createAccessToken(account)
        val postIdx = Random.nextLong().absoluteValue

        val updatedContent = TestUtil.data().post().content()

        mvc.perform(put("/api/v1/post/${postIdx}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(UpdatePostRequest(updatedContent)))
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer $accessToken"))
                .andDo{ println(it.response.contentAsString) }
                .andExpect(status().`is`(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("status", `is`(ErrorStatusType.POLICY_VIOLATION.name)))
                .andExpect(jsonPath("message", `is`(notNullValue())))
                .andExpect(jsonPath("details", `is`(notNullValue())))
    }

    @Test @DisplayName("게시글 수정 통합테스트 - 게시글 작성자가 아닐경우")
    fun testUpdatePost_게시글_작성자가_아닐경우() {
        val writerAccount = createAccount()
        val writerAccessToken = createAccessToken(writerAccount)
        val postIdx = createPost(writerAccessToken).idx
        val issuerAccount = createAccount()
        val issuerAccessToken = createAccessToken(issuerAccount)

        val updatedContent = TestUtil.data().post().content()

        mvc.perform(put("/api/v1/post/${postIdx}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(UpdatePostRequest(updatedContent)))
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer $issuerAccessToken"))
                .andDo{ println(it.response.contentAsString) }
                .andExpect(status().`is`(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("status", `is`(ErrorStatusType.POLICY_VIOLATION.name)))
                .andExpect(jsonPath("message", `is`(notNullValue())))
                .andExpect(jsonPath("details", `is`(notNullValue())))
    }

    @Test @DisplayName("게시글 작성 통합테스트 - 작성 성공")
    fun testCreatePost() {
        val account = createAccount()
        val accessToken = createAccessToken(account)

        val title = TestUtil.data().post().title()
        val content = TestUtil.data().post().content()
        val category = CategoryType.values().random()
        val board = BoardType.values().random()
        val request = CreatePostRequest(title, content, category, board)

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

    @Test @DisplayName("게시글 작성 통합테스트 - 일일 작성한도를 초과하였을 경우")
    fun testCreatePost_일일_작성한도를_초과하였을_경우() {
        val account = createAccount()
        val accessToken = createAccessToken(account)

        val title = TestUtil.data().post().title()
        val content = TestUtil.data().post().content()
        val category = CategoryType.values().random()
        val board = BoardType.values().random()
        val request = CreatePostRequest(title, content, category, board)

        DailyPostCount(account.idx, postProperties.dailyPostLimit, board).let { TestUtil.command().post().saveDailyCount(it) }

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
}